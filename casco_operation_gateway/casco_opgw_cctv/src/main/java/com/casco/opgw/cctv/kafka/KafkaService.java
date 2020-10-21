package com.casco.opgw.cctv.kafka;

public interface KafkaService {
    void sendAlarmMessage(String data);

    void sendStateMessage(String data);
}
