package com.casco.opgw.alarmhandler.kafka.impl;

import com.casco.opgw.alarmhandler.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Value("${kafka.casco_opgw_train_alarm_topic}")
    private String casco_opgw_train_alarm;

    @Value("${kafka.casco_opgw_iscs_alarm_topic}")
    private String casco_opgw_iscs_alarm;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendTrainAlarmMessage(String data) {
        kafkaTemplate.send(casco_opgw_train_alarm, data);
    }

    @Override
    public void sendSCSIAlarmMessage(String data) {
        kafkaTemplate.send(casco_opgw_iscs_alarm, data);
    }
}
