package com.casco.opgw.combinealarm.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.casco.opgw.combinealarm.service.AnalysisService;
import com.casco.opgw.combinealarm.kafka.KafkaConsumer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@AutoConfigureBefore({KafkaConsumer.class, AnalysisService.class})
public class DataSourceConfig {

    @Value("${spring.hive.url}")
    private String url;

    @Value("${spring.hive.driverClass}")
    private String driver;

    @Value("${spring.hive.user}")
    private String user;

    @Value("${spring.hive.password}")
    private String password;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.mysql")
    public javax.sql.DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSource hive() {
        DataSource datSrc = new DataSource();
        datSrc.setUrl(url);
        datSrc.setDriverClassName(driver);
        datSrc.setUsername(user);
        datSrc.setPassword(password);
        return datSrc;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("hive") DataSource hive) { return new JdbcTemplate(hive); }
}
