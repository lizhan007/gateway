package com.casco.opgw.combinealarm.db;

import com.casco.opgw.combinealarm.business.AnalysisService;
import com.casco.opgw.combinealarm.kafka.KafkaConsumer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@AutoConfigureBefore({KafkaConsumer.class, AnalysisService.class})
public class HiveConfig {

    @Value("${spring.hive.url}")
    private String url;

    @Value("${spring.hive.driverClass}")
    private String driver;

    @Value("${spring.hive.user}")
    private String user;

    @Value("${spring.hive.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DataSource datSrc = new DataSource();
        datSrc.setUrl(url);
        datSrc.setDriverClassName(driver);
        datSrc.setUsername(user);
        datSrc.setPassword(password);
        return datSrc;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
