package com.casco.opgw.kafkatoinfluxdb.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.signal.AnalogMessage;
import com.casco.opgw.com.message.signal.DigitMessage;
import com.casco.opgw.com.message.signal.EnumMessage;
import com.casco.opgw.com.message.signal.KafkaConstant;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    private InfluxDB influxDB;

    private final ThreadPoolExecutor executor
            = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvDigitMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());
        System.out.println(3);
        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            return;
        }

        Point.Builder builder = Point.measurement("SIG_DIGIT");
        builder.time(digitMessage.getTimestamp(),TimeUnit.SECONDS);
        builder.addField("value",digitMessage.getValue());
        builder.tag("line", digitMessage.getLineTag());
        builder.tag("region",digitMessage.getRegionTag());
        builder.tag("srcId",digitMessage.getSrcIdTag());
        builder.tag("type",digitMessage.getTypeTag());
        builder.tag("pointcode",digitMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").setRetentionPolicy("52w").write(point);

    }


    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvEnumMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value()) ;
        EnumMessage enumMessage = JSON.parseObject(consumerRecord.value(), EnumMessage.class);

        if(enumMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            return;
        }
        Point.Builder builder = Point.measurement("SIG_ENUM");
        builder.time(enumMessage.getTimestamp(),TimeUnit.SECONDS);
        builder.addField("value",enumMessage.getValue());
        builder.tag("line", enumMessage.getLineTag());
        builder.tag("region",enumMessage.getRegionTag());
        builder.tag("srcId",enumMessage.getSrcIdTag());
        builder.tag("type",enumMessage.getTypeTag());
        builder.tag("pointcode",enumMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").write(point);
    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());
        System.out.println(2);
        AnalogMessage analogMessage = JSON.parseObject(consumerRecord.value(), AnalogMessage.class);

        if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            return;
        }

        Point.Builder builder = Point.measurement("SIG_ANALOG");
        builder.time(analogMessage.getTimestamp(),TimeUnit.SECONDS);
        builder.addField("value",analogMessage.getValue());
        builder.tag("line", analogMessage.getLineTag());
        builder.tag("region",analogMessage.getRegionTag());
        builder.tag("srcId",analogMessage.getSrcIdTag());
        builder.tag("type",analogMessage.getTypeTag());
        builder.tag("pointcode",analogMessage.getPointcodeTag());
        Point point = builder.build();
        influxDB.setDatabase("SIG").setRetentionPolicy("52w").write(point);

    }
}
