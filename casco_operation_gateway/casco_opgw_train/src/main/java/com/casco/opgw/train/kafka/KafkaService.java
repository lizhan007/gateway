package com.casco.opgw.train.kafka;

public interface KafkaService {
    void sendDigitMessage(String data);
    void sendAnalogMessage(String data);
}
