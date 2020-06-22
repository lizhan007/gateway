package com.casco.devdata;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.casco.devdata.mapper")
public class DevDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevDataApplication.class, args);
    }
}
