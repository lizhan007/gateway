package com.casco.opgw.cctv.kafka.impl;


import com.casco.opgw.cctv.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Value("${kafka.casco_opgw_cctv_alarm_topic}")
    private String casco_opgw_cctv_alarm_topic;
    @Value("${kafka.casco_opgw_cctv_state_topic}")
    private String casco_opgw_cctv_state_topic;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendAlarmMessage(String data) {
        kafkaTemplate.send(casco_opgw_cctv_alarm_topic, data);
    }

    @Override
    public void sendStateMessage(String data) {
        kafkaTemplate.send(casco_opgw_cctv_state_topic, data);
    }
}
