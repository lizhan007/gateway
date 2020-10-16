package com.casco.opgw.cctv.kafka;

public interface KafkaServiceCallback<T> {
    void onSuccess(T msg);
    void onError(Throwable e);
}
