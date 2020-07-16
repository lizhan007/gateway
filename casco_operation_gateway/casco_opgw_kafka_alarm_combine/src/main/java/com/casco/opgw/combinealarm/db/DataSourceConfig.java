package com.casco.opgw.combinealarm.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.casco.opgw.combinealarm.service.AnalysisService;
import com.casco.opgw.combinealarm.kafka.KafkaConsumer;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
@AutoConfigureBefore({KafkaConsumer.class, AnalysisService.class})
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.mysql")
    public javax.sql.DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }


}
