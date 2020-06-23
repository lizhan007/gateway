package com.casco.operationportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
public class CascoOperationPortalApplition {
    public static void main(String[] args) {
        SpringApplication.run(CascoOperationPortalApplition.class, args);
    }
}
