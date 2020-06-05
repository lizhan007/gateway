package com.casco.opgw.kafkaalarm;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.casco.opgw.kafkaalarm.mapper")
public class KafkaAlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaAlarmApplication.class, args);
    }

}
