package com.casco.opgw.combinealarm.business;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.combinealarm.business.impl.CacheManagerImpl;
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

    private static final Integer WAIT_NUM = 10;

    private static final Long WAIT_TIME = 60000L;

    @Async("executor")
    public void startAnalysis(BaseMessage baseMessage, Map<String, Object> mapCfg, boolean isAlarm) {
        if (baseMessage instanceof AnalogMessage) {
            AnalogMessage analogMessage = (AnalogMessage)baseMessage;
            analysisTask(analogMessage, mapCfg, analogMessage.getTimestamp(), KeyUtils.getKey(analogMessage), isAlarm);
        } else if (baseMessage instanceof DigitMessage) {
            DigitMessage digitMessage = (DigitMessage)baseMessage;
            analysisTask(digitMessage, mapCfg, digitMessage.getTimestamp(), KeyUtils.getKey(digitMessage), isAlarm);
        } else if (baseMessage instanceof EnumMessage) {
            EnumMessage enumMessage = (EnumMessage)baseMessage;
            analysisTask(enumMessage, mapCfg, enumMessage.getTimestamp(), KeyUtils.getKey(enumMessage), isAlarm);
        }
    }

    private void analysisTask(BaseMessage baseMessage,
                              Map<String, Object> mapCfg, Long timestamp, String key, boolean isAlarm) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String eventType = mapCfg.get(TableInfoConstant.EVENT_INFO + ".event_type").toString();
        if (isAlarm) {
            // 从hive取出车辆专业点位对应报警信息
            StringBuilder sql = new StringBuilder();
            sql.append("select B.* from ");
            sql.append(TableInfoConstant.EVENT_INFO);
            sql.append(" A join ");
            sql.append(TableInfoConstant.EVENT_ALARM_INFO);
            sql.append(" B on A.id = B.event_type_id where A.event_type = ");
            sql.append(eventType);
            List<Map<String, Object>> eventAlarmInfo = jdbcTemplate.queryForList(sql.toString());

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
                sqlForVeh.append(eventAlarmInfo.get(i).get("b.arm_veh_code").toString());
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
            alarmData.setArmContent(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_content").toString());
            alarmData.setArmDbm(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_dbm").toString());
            alarmData.setArmEquName(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_equ_name").toString());
            alarmData.setArmEquCode(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_equ_code").toString());
            alarmData.setArmEquType(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_equ_type").toString());
            alarmData.setArmLevel(
                    Float.parseFloat(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_level").toString()));
            alarmData.setArmEquTypecode(
                    Float.parseFloat(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_equ_type_code").toString()));
            alarmData.setArmCode(
                    Float.parseFloat(mapCfg.get(TableInfoConstant.EVENT_INFO + ".root_arm_code").toString()));

            // 代码保护
            if (eventAlarmInfo.size() != 0) {
                String majorName = eventAlarmInfo.get(0).get("b.arm_veh_code").toString().split("\\.")[0];
                String lineName = eventAlarmInfo.get(0).get("b.arm_veh_code").toString().split("\\.")[1];
                String trainName = eventAlarmInfo.get(0).get("b.arm_veh_code").toString().split("\\.")[2];

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

                labelA:
                for (Map<String, Object> veh : vehCodeInfo) {
                    String alarmCode = veh.get("a.key_id").toString();
                    String alarmValue = veh.get("a.value").toString();

                    // 去匹配报警相关信息
                    for (Map<String, Object> alarm : eventAlarmInfo) {
                        // 找到原因：找到最早变位的点
                        if (alarm.get("b.arm_veh_code").toString().equals(alarmCode)
                                && alarm.get("b.arm_code_value").toString().equals(alarmValue)) {
                            alarmData.setArmContent(alarm.get("b.arm_content").toString());
                            alarmData.setArmDbm(alarm.get("b.arm_dbm").toString());
                            alarmData.setArmEquName(alarm.get("b.arm_equ_name").toString());
                            alarmData.setArmEquType(alarm.get("b.arm_equ_type").toString());
                            alarmData.setArmEquCode(alarm.get("b.arm_equ_code").toString());
                            alarmData.setArmEquTypecode(
                                    Float.parseFloat(alarm.get("b.arm_equ_type_code").toString()));
                            alarmData.setArmLevel(Float.parseFloat(alarm.get("b.arm_level").toString()));
                            alarmData.setArmCode(Float.parseFloat(alarm.get("b.arm_code").toString()));
                            break labelA;
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
