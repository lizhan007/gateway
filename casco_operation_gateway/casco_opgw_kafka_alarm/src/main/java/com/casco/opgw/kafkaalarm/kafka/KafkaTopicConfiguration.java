package com.casco.opgw.kafkaalarm.kafka;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaTopicConfiguration {

    private final KafkaProperties properties;

    public KafkaTopicConfiguration(KafkaProperties kafkaProperties) {
        this.properties = kafkaProperties;
    }

    @Bean
    public String[] kafkaTopicName() {
        return properties.getTopicName();
    }

    @Bean
    public String topicGroupId() {
        return properties.getGroupId();
    }
}
