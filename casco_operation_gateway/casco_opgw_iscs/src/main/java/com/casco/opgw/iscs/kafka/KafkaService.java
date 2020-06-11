package com.casco.opgw.iscs.kafka;

public interface KafkaService {
    void sendDigitMessage(String data);
    void sendAnalogMessage(String data);
}
