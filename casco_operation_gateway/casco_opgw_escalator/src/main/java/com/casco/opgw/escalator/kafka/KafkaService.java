package com.casco.opgw.escalator.kafka;

public interface KafkaService {
    void sendDigitMessage(String data);
    void sendAnalogMessage(String data);
}
