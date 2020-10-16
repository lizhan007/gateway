package com.casco.opgw.screendoor.kafka;

public interface KafkaService {
    void sendAlarmMessage(String data);
    void sendAnalogMessage(String data);
}
