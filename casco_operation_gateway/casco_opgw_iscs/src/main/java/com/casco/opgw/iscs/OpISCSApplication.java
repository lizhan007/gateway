package com.casco.opgw.iscs;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class OpISCSApplication {

    public static Map<String, String> global_params = new HashMap<>();

    public static void main(String[] args) {

        for(String param : args){

            String key   = param.split("=")[0];
            String value = param.split("=")[1];
            global_params.put(key, value);
        }

        SpringApplication.run(OpISCSApplication.class, args);
    }

}
