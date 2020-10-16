package com.casco.opgw.screendoor.kafka;

public interface KafkaServiceCallback<T> {
    void onSuccess(T msg);
    void onError(Throwable e);
}
