package com.casco.opgw.train;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class OpgwTrainApplication {

    public static Map<String, String> global_params = new HashMap<>();


    public static void main(String[] args) {

        log.info(args[0].toString());

        /*-
         * 初始化参数
         * OpgwTrainApplication 基础启动命令为
         * java -jar xx.jar line=XXX station=xxx
         */
        for(String param : args){

            String key   = param.split("=")[0];
            String value = param.split("=")[1];
            global_params.put(key, value);
        }

        SpringApplication.run(OpgwTrainApplication.class, args);
    }
}
