package com.casco.opgw.kafkatoinfluxdb.influxdb;



import com.casco.opgw.kafkatoinfluxdb.kafka.KafkaConsumer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@AutoConfigureBefore(KafkaConsumer.class)
public class InfluxDBConfig {

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.url}")
    private String url;

    private String database;

    private InfluxDB influxDB;

    @Bean(name = "SIG")
    public InfluxDB SIGInfluxDB1(){
        InfluxDB influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase("SIG")
                .enableBatch(20,200, TimeUnit.MILLISECONDS);

        return influxDB;
    }

    @Bean(name = "TRAIN")
    public InfluxDB TrainInfluxDB1(){
        InfluxDB influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase("TRAIN")
                .enableBatch(20,200, TimeUnit.MILLISECONDS);

        return influxDB;
    }

    @Bean(name = "BAS")
    public InfluxDB BASInfluxDB1(){
        InfluxDB influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase("BAS")
                .enableBatch(20,200, TimeUnit.MILLISECONDS);

        return influxDB;
    }

    public InfluxDBConfig() {

    }

    public InfluxDBConfig(String userName, String password, String url, String database) {
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.database = database;
        build();
    }

    public InfluxDBConfig(String database) {
        this.database = database;
        build();
    }


    private void build(){

        if(influxDB == null){
            influxDB = InfluxDBFactory.connect(this.url,this.userName,this.password);
        }
        influxDB.setDatabase(this.database);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

}
