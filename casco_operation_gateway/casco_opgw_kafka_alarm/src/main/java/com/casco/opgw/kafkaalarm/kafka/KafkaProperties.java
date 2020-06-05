package com.casco.opgw.kafkaalarm.kafka;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@ConfigurationProperties("kafka.topic")
@Data
public class KafkaProperties implements Serializable {

    public String   groupId;
    public String[] topicName;

}
