package com.casco.opgw.combinealarm.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.combinealarm.entity.SysEventAlarmInfo;
import com.casco.opgw.combinealarm.entity.SysEventInfo;
import com.casco.opgw.combinealarm.mapper.SysEventAlarmInfoMapper;
import com.casco.opgw.combinealarm.service.impl.CacheManagerImpl;
import com.casco.opgw.combinealarm.db.TableInfoConstant;
import com.casco.opgw.combinealarm.dto.AlarmData;
import com.casco.opgw.combinealarm.kafka.KafkaProducer;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AnalysisService {
    @Value("${spring.analysis.duration}")
    private Long duration;

    @Value("${spring.analysis.waitTime}")
    private Long waitTime;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Resource(name = "TRAIN")
    private InfluxDB trainInfluxDB;

    @Autowired
    private SysEventAlarmInfoMapper sysEventAlarmInfoMapper;

    private static final Integer WAIT_NUM = 10;

    private static final Long WAIT_TIME = 60000L;

    @Async("executor")
    public void startAnalysis(BaseMessage baseMessage, SysEventInfo sysEventInfo, boolean isAlarm) {
        if (baseMessage instanceof AnalogMessage) {
            AnalogMessage analogMessage = (AnalogMessage)baseMessage;
            analysisTask(analogMessage, sysEventInfo, analogMessage.getTimestamp(), KeyUtils.getKey(analogMessage), isAlarm);
        } else if (baseMessage instanceof DigitMessage) {
            DigitMessage digitMessage = (DigitMessage)baseMessage;
            analysisTask(digitMessage, sysEventInfo, digitMessage.getTimestamp(), KeyUtils.getKey(digitMessage), isAlarm);
        } else if (baseMessage instanceof EnumMessage) {
            EnumMessage enumMessage = (EnumMessage)baseMessage;
            analysisTask(enumMessage, sysEventInfo, enumMessage.getTimestamp(), KeyUtils.getKey(enumMessage), isAlarm);
        }
    }

    private void insatllAlarm(AlarmData alarmData, SysEventAlarmInfo sysEventAlarmInfo) {
        alarmData.setArmContent(sysEventAlarmInfo.getArmContent());
        alarmData.setArmDbm(sysEventAlarmInfo.getArmDbm());
        alarmData.setArmSource(sysEventAlarmInfo.getArmSource());
        alarmData.setArmEquName(sysEventAlarmInfo.getArmEquName());
        alarmData.setArmEquType(sysEventAlarmInfo.getArmEquType());
        alarmData.setArmEquCode(sysEventAlarmInfo.getArmEquCode());
        alarmData.setArmEquTypecode(sysEventAlarmInfo.getArmEquTypeCode());
        alarmData.setArmLevel(sysEventAlarmInfo.getArmLevel());
        alarmData.setArmCode(sysEventAlarmInfo.getArmCode());

    }

    // 适用单条查询语句
    private List<Map<String, Object>> packageData(QueryResult results) {
        if (results.getResults() == null) {
            return null;
        }

        QueryResult.Result oneResult = results.getResults().get(0);
        List<QueryResult.Series> seriesList = oneResult.getSeries();
        // 封装查询结果
        List<Map<String, Object>> dataList = new LinkedList<>();
        for (QueryResult.Series series : seriesList) {
            List<List<Object>> values = series.getValues();       //字段字集合
            List<String> columns = series.getColumns();           //字段名

            for (List<Object> value : values) {
                Map<String, Object> dataMap = new HashMap<>(columns.size());
                for (int j = 0; j < columns.size(); ++j){
                    dataMap.put(columns.get(j),value.get(j));
                }
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

    private void analysisTask(BaseMessage baseMessage,
                              SysEventInfo sysEventInfo, Long timestamp, String key, boolean isAlarm) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Integer eventType = sysEventInfo.getEventType();
        if (isAlarm) {
            List<SysEventAlarmInfo> eventAlarmInfo = sysEventAlarmInfoMapper.selectList(
                    new QueryWrapper<SysEventAlarmInfo>().eq("event_type_id", sysEventInfo.getId())
            );

            AlarmData alarmData = new AlarmData();
            alarmData.setLineName(baseMessage.getLineTag());
            alarmData.setArmUuid(UUID.randomUUID().toString());
            alarmData.setArmHappenTime(
                    LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
            alarmData.setArmContent(sysEventInfo.getRootArmContent());
            alarmData.setArmDbm(sysEventInfo.getRootArmDbm());
            alarmData.setArmSource(sysEventInfo.getRootArmSource());
            alarmData.setArmEquName(sysEventInfo.getRootArmEquName());
            alarmData.setArmEquCode(sysEventInfo.getRootArmEquCode());
            alarmData.setArmEquType(sysEventInfo.getRootArmEquType());
            alarmData.setArmLevel(sysEventInfo.getRootArmLevel());
            alarmData.setArmEquTypecode(sysEventInfo.getRootArmEquTypeCode());
            alarmData.setArmCode(sysEventInfo.getRootArmCode());

            // 防止查询语句出错
            if (eventAlarmInfo.size() != 0) {
                String startTime = LocalDateTime.ofEpochSecond(timestamp - duration, 0,
                        ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));
                String endTime = LocalDateTime.ofEpochSecond(timestamp + duration, 0,
                        ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));
                String cmdPrefix =
                        String.format("SELECT * FROM \"%s\" WHERE time > '%s' AND time < '%s' ", TableInfoConstant.VEHICLE_TABLE_DIGIT,startTime, endTime);

                // 从influxDB查询车辆是否为维修状态
                String isMaintain =
                        String.format("%s AND pointcode = '%S' ORDER BY time DESC LIMIT 1 tz('Asia/Shanghai')", cmdPrefix, TableInfoConstant.MAINTAIN_CODE_NAME);
                Query queryMaintain = new Query(isMaintain, TableInfoConstant.VEHICLE_DB_NAME);
                QueryResult results = trainInfluxDB.query(queryMaintain);

                List<Map<String, Object>> maintains = packageData(results);
                if (maintains != null && maintains.size() > 0) {
                    Map<String, Object> maintain = maintains.get(0);
                    alarmData.setArmIsMaintain(Float.parseFloat(maintain.get("value").toString()));
                }

                // 从influxDB查询车辆点位数据
                StringBuilder cmdForVeh = new StringBuilder();
                cmdForVeh.append(String.format("%s AND (", cmdPrefix));
                for (int i = 0; i < eventAlarmInfo.size(); i++) {
                    cmdForVeh.append("pointcode = '");
                    cmdForVeh.append(eventAlarmInfo.get(i).getArmVehCode());
                    if (i != eventAlarmInfo.size() - 1) {
                        cmdForVeh.append("' OR ");
                    } else {
                        cmdForVeh.append("') ORDER BY time tz('Asia/Shanghai')");
                    }
                }
                Query queryForVeh = new Query(cmdForVeh.toString(), TableInfoConstant.VEHICLE_DB_NAME);
                QueryResult resultsForVeh = trainInfluxDB.query(queryForVeh);
                List<Map<String, Object>> vehCodeInfo = packageData(resultsForVeh);

                if (vehCodeInfo != null) {
                    boolean isRunning = false;
                    String[] running = TableInfoConstant.VEHICLE_SKIDDING_RUNNING.split(",");
                    labelA:
                    for (Map<String, Object> veh : vehCodeInfo) {
                        String alarmCode = veh.get("pointcode").toString();
                        Float alarmValue = Float.parseFloat(veh.get("value").toString());

                        // 匹配是否车轮打滑
                        if (!Arrays.asList(running).contains(alarmCode)) {
                            for (SysEventAlarmInfo sysEventAlarmInfo : eventAlarmInfo) {
                                // 找到原因：找到最早变位的点
                                if (sysEventAlarmInfo.getArmVehCode().equals(alarmCode)
                                        && (sysEventAlarmInfo.getArmCodeValue() - alarmValue) == 0) {
                                    insatllAlarm(alarmData, sysEventAlarmInfo);
                                    isRunning = true;
                                    break labelA;
                                }
                            }
                        }
                    }

                    // 是车轮打滑，继续分析是否走行部异常
                    if (isRunning && eventType.equals(0)) {
                        labelB:
                        for (Map<String, Object> veh : vehCodeInfo) {
                            String alarmCode = veh.get("pointcode").toString();
                            Float alarmValue = Float.parseFloat(veh.get("value").toString());

                            if (Arrays.asList(running).contains(alarmCode)) {
                                // 去匹配报警相关信息
                                for (SysEventAlarmInfo sysEventAlarmInfo : eventAlarmInfo) {
                                    // 找到原因：找到最早变位的点
                                    if (sysEventAlarmInfo.getArmVehCode().equals(alarmCode)
                                            && (sysEventAlarmInfo.getArmCodeValue() - alarmValue) == 0) {
                                        insatllAlarm(alarmData, sysEventAlarmInfo);
                                        break labelB;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 写入缓存
            CacheManagerImpl cacheManager = new CacheManagerImpl();
            cacheManager.putCache(key, alarmData);
            kafkaProducer.sendAlarmMessage(JSON.toJSONString(alarmData));
        }
        else {
            // 报警恢复
            CacheManagerImpl cacheManager = new CacheManagerImpl();
            for (int i = 0; i < WAIT_NUM; i++) {
                if (cacheManager.isContains(key)) {
                    AlarmData alarmData = cacheManager.getCacheDataByKey(key);
                    alarmData.setArmRestoreTime(
                            LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
                    kafkaProducer.sendAlarmMessage(JSON.toJSONString(alarmData));
                    cacheManager.clearByKey(key);
                    break;
                }

                // 报警恢复需等待报警分析完成
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
