package com.casco.opgw.signal.kafka;

public interface KafkaServiceCallback<T> {
    void onSuccess(T msg);
    void onError(Throwable e);
}
