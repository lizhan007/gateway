package com.casco.opgw.alarmhandler;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.casco.opgw.alarmhandler.mapper")
public class AlarmHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmHandlerApplication.class, args);
    }
}
