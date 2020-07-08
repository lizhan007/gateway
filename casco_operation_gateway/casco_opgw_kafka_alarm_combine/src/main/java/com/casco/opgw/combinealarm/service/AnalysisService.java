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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AnalysisService {
    @Value("${spring.analysis.duration}")
    private Long duration;

    @Value("${spring.analysis.waitTime}")
    private Long waitTime;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InfluxDB influxDB;

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

            // 从车辆表中获取点位信息
            StringBuilder sqlForVeh = new StringBuilder();
            sqlForVeh.append("select * from ");
            sqlForVeh.append(TableInfoConstant.VEHICLE_TABLE_DIGIT);
            sqlForVeh.append(" A where (unix_timestamp(A.record_time) >= ");
            sqlForVeh.append(timestamp - duration);
            sqlForVeh.append(" and unix_timestamp(A.record_time) <= ");
            sqlForVeh.append(timestamp + duration);
            sqlForVeh.append(") and (");
            for (int i = 0; i < eventAlarmInfo.size(); i++) {
                sqlForVeh.append("A.key_id = '");
                sqlForVeh.append(eventAlarmInfo.get(i).getArmVehCode());
                if (i != eventAlarmInfo.size() - 1) {
                    sqlForVeh.append("' or ");
                } else {
                    sqlForVeh.append("') order by A.record_time");
                }
            }

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

            // 代码保护
            if (eventAlarmInfo.size() != 0) {
                String majorName = eventAlarmInfo.get(0).getArmVehCode().split("\\.")[0];
                String lineName = eventAlarmInfo.get(0).getArmVehCode().split("\\.")[1];
                String trainName = eventAlarmInfo.get(0).getArmVehCode().split("\\.")[2];

                // 获取OVERHAUL_MODE信息
                String isMaintainCodeName = majorName + "." + lineName + "." + trainName + "." + "OVERHAUL_MODE";
                StringBuilder sqlIsMaintain = new StringBuilder();
                sqlIsMaintain.append("select * from ");
                sqlIsMaintain.append(TableInfoConstant.VEHICLE_TABLE_DIGIT);
                sqlIsMaintain.append(" A where unix_timestamp(A.record_time) >= ");
                sqlIsMaintain.append(timestamp - duration);
                sqlIsMaintain.append(" and unix_timestamp(A.record_time) <= ");
                sqlIsMaintain.append(timestamp + duration);
                sqlIsMaintain.append(" and A.key_id = '");
                sqlIsMaintain.append(isMaintainCodeName);
                sqlIsMaintain.append("' limit 1");
                List<Map<String, Object>> vehIsMaintain = jdbcTemplate.queryForList(sqlIsMaintain.toString());
                if (vehIsMaintain.size() != 0) {
                    Map<String, Object> isMaintain = vehIsMaintain.get(0);
                    alarmData.setArmIsMaintain(Float.parseFloat(isMaintain.get("a.value").toString()));
                }

                List<Map<String, Object>> vehCodeInfo = jdbcTemplate.queryForList(sqlForVeh.toString());

                boolean isRunning = false;
                String[] running = TableInfoConstant.VEHICLE_SKIDDING_RUNNING.split(",");
                labelA:
                for (Map<String, Object> veh : vehCodeInfo) {
                    String alarmCode = veh.get("a.key_id").toString();
                    String alarmValue = veh.get("a.value").toString();
                    // 去匹配报警相关信息
                    for (SysEventAlarmInfo sysEventAlarmInfo : eventAlarmInfo) {
                        // 找到原因：找到最早变位的点
                        if (sysEventAlarmInfo.getArmVehCode().equals(alarmCode)
                                && sysEventAlarmInfo.getArmCodeValue().toString().equals(alarmValue)) {
                            insatllAlarm(alarmData, sysEventAlarmInfo);
                            isRunning = true;
                            break labelA;
                        }
                    }
                }

                // 走行部异常
                if (isRunning && eventType.equals(0)) {
                    labelB:
                    for (Map<String, Object> veh : vehCodeInfo) {
                        String alarmCode = veh.get("a.key_id").toString();
                        String alarmValue = veh.get("a.value").toString();

                        if (Arrays.asList(running).contains(alarmCode)) {
                            // 去匹配报警相关信息
                            for (SysEventAlarmInfo sysEventAlarmInfo : eventAlarmInfo) {
                                // 找到原因：找到最早变位的点
                                if (sysEventAlarmInfo.getArmVehCode().equals(alarmCode)
                                        && sysEventAlarmInfo.getArmCodeValue().toString().equals(alarmValue)) {
                                    insatllAlarm(alarmData, sysEventAlarmInfo);
                                    break labelB;
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
