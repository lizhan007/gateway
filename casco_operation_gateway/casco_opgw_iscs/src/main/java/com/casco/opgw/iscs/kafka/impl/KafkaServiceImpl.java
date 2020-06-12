package com.casco.opgw.iscs.kafka.impl;

import com.casco.opgw.iscs.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Value("${kafka.casco_opgw_iscs_digit_topic}")
    private String casco_opgw_iscs_digit_topic;

    @Value("${kafka.casco_opgw_iscs_analog_topic}")
    private String casco_opgw_iscs_analog_topic;

    //@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendDigitMessage(String data) {

        System.out.println("sendDigitMessage " + data);
        //kafkaTemplate.send(casco_opgw_iscs_digit_topic, data);
    }

    @Override
    public void sendAnalogMessage(String data) {
        System.out.println("sendAnalogMessage " + data);
        //kafkaTemplate.send(casco_opgw_iscs_analog_topic, data);
    }
}
