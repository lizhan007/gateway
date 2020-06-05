package com.casco.opgw.signal.kafka;

public interface KafkaService {
    void sendDigitMessage(String data);
    void sendEnumMessage(String data);
    void sendAnalogMessage(String data);
    void sendAlarmMessage(String data);
}
