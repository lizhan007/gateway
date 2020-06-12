package com.casco.opgw.alarmhandler.kafka;

public interface KafkaService {
    void sendTrainAlarmMessage(String data);
    void sendSCSIAlarmMessage(String data);
}
