package com.casco.siganalysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.casco.siganalysis.mapper")
public class CascoSigAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CascoSigAnalysisApplication.class, args);
    }

}
