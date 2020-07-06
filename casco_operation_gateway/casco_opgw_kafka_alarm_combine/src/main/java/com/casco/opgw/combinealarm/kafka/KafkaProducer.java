package com.casco.opgw.combinealarm.kafka;

import com.casco.opgw.combinealarm.kafka.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private  KafkaProperties kafkaProperties;

    public void sendAlarmMessage(String data) {
        kafkaTemplate.send(kafkaProperties.getTopicName(), data);
    }
}
