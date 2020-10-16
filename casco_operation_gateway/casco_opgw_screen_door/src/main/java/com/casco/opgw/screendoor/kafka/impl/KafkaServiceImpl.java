package com.casco.opgw.screendoor.kafka.impl;

import com.casco.opgw.screendoor.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Value("${kafka.casco_opgw_signal_analog_topic}")
    private String casco_opgw_signal_analog_topic;
    @Value("${kafka.casco_opgw_signal_alarm_topic}")
    private String casco_opgw_signal_alarm_topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendAlarmMessage(String data) {
        kafkaTemplate.send(casco_opgw_signal_analog_topic, data);
    }

    @Override
    public void sendAnalogMessage(String data) {
        kafkaTemplate.send(casco_opgw_signal_alarm_topic, data);
    }
}
