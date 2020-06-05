package com.casco.opgw.signal.kafka.impl;


import com.casco.opgw.signal.kafka.config.KafkaProperties;
import com.casco.opgw.signal.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private  KafkaProperties kafkaProperties;

    @Override
    public void sendDigitMessage(String data) {
        kafkaTemplate.send(kafkaProperties.getTopicName()[0], data);
    }

    @Override
    public void sendEnumMessage(String data) {

        System.out.println(kafkaProperties.getTopicName()[1] + "  " + data);
        kafkaTemplate.send(kafkaProperties.getTopicName()[1], data);
    }

    @Override
    public void sendAnalogMessage(String data) {
        kafkaTemplate.send(kafkaProperties.getTopicName()[2], data);
    }

    @Override
    public void sendAlarmMessage(String data) {
        kafkaTemplate.send(kafkaProperties.getTopicName()[3], data);
    }
}
