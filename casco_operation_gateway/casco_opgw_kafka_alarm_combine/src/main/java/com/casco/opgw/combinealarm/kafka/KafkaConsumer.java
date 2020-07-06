package com.casco.opgw.combinealarm.kafka;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.combinealarm.business.AnalysisService;
import com.casco.opgw.combinealarm.db.TableInfoConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class KafkaConsumer {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Executor executor;

    @Autowired
    private AnalysisService analysisService;

    final private String sqlQuery = "select * from " + TableInfoConstant.EVENT_INFO;

    private static List<Map<String, Object>> list = null;

    private static Map<String, Boolean> eventFlag = new HashMap<String, Boolean>();

    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_combine_alarm")
    public void recvDigitMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)) {
            return;
        }

        if (list == null) {
            synchronized (this) {
                list = jdbcTemplate.queryForList(sqlQuery);
            }
        }

        for (Map<String, Object> map : list) {
            String codeName = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_name").toString();
            String codeValue = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_value").toString();

            String key = KeyUtils.getKey(digitMessage);
            if (codeName.equals(key)) {
                if (codeValue.equals(Integer.toString(digitMessage.getValue()))) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startAnalysis(digitMessage, map, true);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.startAnalysis(digitMessage, map, false);
                    }
                }
            }
        }
    }

    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_combine_alarm")
    public void recvEnumMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        EnumMessage enumMessage = JSON.parseObject(consumerRecord.value(), EnumMessage.class);

        if(enumMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            return;
        }

        if (list == null) {
            synchronized (this) {
                list = jdbcTemplate.queryForList(sqlQuery);
            }
        }

        for (Map<String, Object> map : list) {
            String codeName = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_name").toString();
            String codeValue = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_value").toString();

            String key = KeyUtils.getKey(enumMessage);
            if (codeName.equals(key)) {
                if (codeValue.equals(Integer.toString(enumMessage.getValue()))) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startAnalysis(enumMessage, map, true);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.startAnalysis(enumMessage, map, false);
                    }
                }
            }
        }
    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_combine_alarm")
    public void recvAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        AnalogMessage analogMessage = JSON.parseObject(consumerRecord.value(), AnalogMessage.class);

        if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            return;
        }

        if (list == null) {
            synchronized (this) {
                list = jdbcTemplate.queryForList(sqlQuery);
            }
        }

        for (Map<String, Object> map : list) {
            String codeName = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_name").toString();
            String codeValue = map.get(TableInfoConstant.EVENT_INFO + ".signal_code_value").toString();

            String key = KeyUtils.getKey(analogMessage);
            if (codeName.equals(key)) {
                if (codeValue.equals(Float.toString(analogMessage.getValue()))) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startAnalysis(analogMessage, map, true);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.startAnalysis(analogMessage, map, false);
                    }
                }
            }
        }
    }
}
