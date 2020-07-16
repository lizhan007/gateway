package com.casco.opgw.combinealarm.kafka;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.*;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.combinealarm.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    private AnalysisService analysisService;

    private static Map<String, Object> map = new HashMap<>();

    private static Map<String, Boolean> eventFlag = new HashMap<String, Boolean>();

    static {
        map.put("SIG.9.CDI.90009.E0.39", 1);
    }

    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_combine_alarm")
    public void recvDigitMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)) {
            return;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();

            if (mapValue instanceof Integer) {
                Integer codeValue = (Integer)mapValue;
                String key = KeyUtils.getKey(digitMessage);
                if (mapKey.equals(key) && codeValue.equals(digitMessage.getValue())) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startActiviti(digitMessage, codeValue, digitMessage.getTimestamp(), key);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.alarmRestore(digitMessage.getTimestamp(), key);
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

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();

            if (mapValue instanceof Integer) {
                Integer codeValue = (Integer)mapValue;
                String key = KeyUtils.getKey(enumMessage);
                if (mapKey.equals(key) && codeValue.equals(enumMessage.getValue())) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startActiviti(enumMessage, codeValue, enumMessage.getTimestamp(), key);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.alarmRestore(enumMessage.getTimestamp(), key);
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

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();

            if (mapValue instanceof Float) {
                Float codeValue = (Float)mapValue;
                String key = KeyUtils.getKey(analogMessage);
                if (mapKey.equals(key) && codeValue.equals(analogMessage.getValue())) {
                    // 发生报警
                    if (!eventFlag.containsKey(key)
                            || (eventFlag.containsKey(key) && eventFlag.get(key).equals(false))) {
                        eventFlag.put(key, true);
                        analysisService.startActiviti(analogMessage, codeValue, analogMessage.getTimestamp(), key);
                    }
                } else {
                    // 报警恢复
                    if (eventFlag.containsKey(key) && eventFlag.get(key).equals(true)) {
                        eventFlag.put(key, false);
                        analysisService.alarmRestore(analogMessage.getTimestamp(), key);
                    }
                }
            }
        }
    }
}
