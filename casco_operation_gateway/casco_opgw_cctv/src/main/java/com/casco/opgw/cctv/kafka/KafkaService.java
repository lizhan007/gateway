package com.casco.opgw.cctv.kafka;

public interface KafkaService {
    void sendAlarmMessage(String data);
}
