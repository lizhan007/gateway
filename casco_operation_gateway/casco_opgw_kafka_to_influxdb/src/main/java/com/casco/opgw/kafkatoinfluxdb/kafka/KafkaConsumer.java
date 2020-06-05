package com.casco.opgw.kafkatoinfluxdb.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.signal.DigitMessage;
import com.casco.opgw.com.message.signal.EnumMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumer {

    @Autowired
    private InfluxDB influxDB;

    private final ThreadPoolExecutor executor
            = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_kafka_consumer")
    public void recvDigitMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        Point.Builder builder = Point.measurement("SIG_SWITCH");
        builder.time(digitMessage.getTimestamp(),TimeUnit.MICROSECONDS);
        builder.addField("value",digitMessage.getValue());
        builder.tag("line", digitMessage.getLineTag());
        builder.tag("region",digitMessage.getRegionTag());
        builder.tag("srcId",digitMessage.getSrcIdTag());
        builder.tag("type",digitMessage.getTypeTag());
        builder.tag("pointcode",digitMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").write(point);

    }


    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_kafka_consumer")
    public void recvEnumMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        EnumMessage enumMessage = JSON.parseObject(consumerRecord.value(), EnumMessage.class);

        Point.Builder builder = Point.measurement("SIG_STATUS");
        builder.time(enumMessage.getTimestamp(),TimeUnit.MICROSECONDS);
        builder.addField("value",enumMessage.getValue());
        builder.tag("line", enumMessage.getLineTag());
        builder.tag("region",enumMessage.getRegionTag());
        builder.tag("srcId",enumMessage.getSrcIdTag());
        builder.tag("type",enumMessage.getTypeTag());
        builder.tag("pointcode",enumMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").write(point);

    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_kafka_consumer")
    public void recvAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        System.out.println(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        Point.Builder builder = Point.measurement("SIG_ANALOG");
        builder.time(digitMessage.getTimestamp(),TimeUnit.MICROSECONDS);
        builder.addField("value",digitMessage.getValue());
        builder.tag("line", digitMessage.getLineTag());
        builder.tag("region",digitMessage.getRegionTag());
        builder.tag("srcId",digitMessage.getSrcIdTag());
        builder.tag("type",digitMessage.getTypeTag());
        builder.tag("pointcode",digitMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").write(point);

    }
}
