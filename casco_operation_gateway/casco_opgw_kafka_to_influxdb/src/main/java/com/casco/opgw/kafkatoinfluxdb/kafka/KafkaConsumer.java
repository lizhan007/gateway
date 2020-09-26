package com.casco.opgw.kafkatoinfluxdb.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.kafkatoinfluxdb.influxdb.DataCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class KafkaConsumer {

    @Resource(name = "SIG")
    private InfluxDB SigInfluxDB;

    @Resource(name = "BAS")
    private InfluxDB BasInfluxDB;

    @Resource(name = "TRAIN")
    private InfluxDB TrainInfluxDB;

    private final ThreadPoolExecutor executor
            = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvDigitMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("SIG").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){
            DigitMessage digitMessage = JSON.parseObject(item.value(), DigitMessage.class);
            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("SIG_DIGIT");
            // builder.time(digitMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("region",digitMessage.getRegionTag());
            builder.tag("srcId",digitMessage.getSrcIdTag());
            builder.tag("type",digitMessage.getTypeTag());
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();

            DataCache.addPoint(DataCache.SIG_TYPE, JSON.toJSONString(digitMessage));

            batchPoints.point(point);
        }

        SigInfluxDB.setDatabase("SIG").setRetentionPolicy("52w").write(batchPoints);

    }


    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvEnumMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("SIG").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){
            EnumMessage enumMessage = JSON.parseObject(item.value(), EnumMessage.class);

            if(enumMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }
            Point.Builder builder = Point.measurement("SIG_ENUM");
            // builder.time(enumMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",enumMessage.getValue());
            builder.tag("line", enumMessage.getLineTag());
            builder.tag("region",enumMessage.getRegionTag());
            builder.tag("srcId",enumMessage.getSrcIdTag());
            builder.tag("type",enumMessage.getTypeTag());
            builder.tag("pointcode",enumMessage.getPointcodeTag());
            Point point = builder.build();

            batchPoints.point(point);
        }

        SigInfluxDB.setDatabase("SIG").setRetentionPolicy("52w").write(batchPoints);
    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_kafka_to_influxdb")
    public void recvAnalogMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("SIG").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){

            AnalogMessage analogMessage = JSON.parseObject(item.value(), AnalogMessage.class);

            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("SIG_ANALOG");
            // builder.time(analogMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",analogMessage.getValue());
            builder.tag("line", analogMessage.getLineTag());
            builder.tag("region",analogMessage.getRegionTag());
            builder.tag("srcId",analogMessage.getSrcIdTag());
            builder.tag("type",analogMessage.getTypeTag());
            builder.tag("pointcode",analogMessage.getPointcodeTag());
            Point point = builder.build() ;
            batchPoints.point(point);
        }

        SigInfluxDB.setDatabase("SIG").setRetentionPolicy("52w").write(batchPoints);

    }

    /**
     * 车辆开关量 -- > influxDB写入
     * @param
     */
    @KafkaListener(topics = "casco_opgw_train_digit", groupId = "casco_opgw_train_kafka_to_influxdb")
    public void recvTrainDigitMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("TRAIN").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){

            DigitMessage digitMessage = JSON.parseObject(item.value(),DigitMessage.class);

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("TRAIN_DIGIT");
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("srcId",digitMessage.getSrcIdTag());
            builder.tag("type","");  //Type未知
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();

            DataCache.addPoint(DataCache.TRAIN_TYPE, JSON.toJSONString(digitMessage));
            batchPoints.point(point);
        }

        TrainInfluxDB.setDatabase("TRAIN").setRetentionPolicy("52w").write(batchPoints);
    }

    /**
     * 车辆模拟量 -- > influxDB写入
     * @param
     */
    @KafkaListener(topics = "casco_opgw_train_analog", groupId = "casco_opgw_train_kafka_to_influxdb")
    public  void recvTrainAnalogMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("TRAIN").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){

            AnalogMessage analogMessage = JSON.parseObject(item.value(), AnalogMessage.class);

            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("TRAIN_ANALOG");
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",analogMessage.getValue());
            builder.tag("line", analogMessage.getLineTag());
            builder.tag("srcId",analogMessage.getSrcIdTag());
            builder.tag("type","");  //Type未知
            builder.tag("pointcode",analogMessage.getPointcodeTag());
            Point point = builder.build() ;
            batchPoints.point(point);
        }

        TrainInfluxDB.setDatabase("TRAIN").setRetentionPolicy("52w").write(batchPoints);
    }

    /**
     * BAS数字量  -- > influxDB写入
     * @param
     */
    @KafkaListener(topics = "casco_opgw_iscs_digit", groupId = "casco_opgw_iscs_kafka_to_influxdb")
    public void recvIscsDigitMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("BAS").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){
            DigitMessage digitMessage = JSON.parseObject(item.value(),DigitMessage.class);

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("BAS_DIGIT");
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("region",digitMessage.getRegionTag());
            builder.tag("srcId","水泵系统");
            builder.tag("type","");  //Type未知
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();

            DataCache.addPoint(DataCache.SCSI_TYPE, JSON.toJSONString(digitMessage));
            batchPoints.point(point);

        }

        BasInfluxDB.setDatabase("BAS").setRetentionPolicy("52w").write(batchPoints);
    }


    /**
     * BAS模拟量 -- > influxDB写入
     * @param
     */
    @KafkaListener(topics = "casco_opgw_iscs_analog", groupId = "casco_opgw_iscs_kafka_to_influxdb")
    public void recvIscsAnalogMsg(List<ConsumerRecord<String, String>> consumerRecordList){

        BatchPoints batchPoints
                = BatchPoints.database("BAS").retentionPolicy("52w").build();

        for(ConsumerRecord<String, String> item: consumerRecordList){

            AnalogMessage analogMessage = JSON.parseObject(item.value(), AnalogMessage.class);

            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                continue;
            }

            Point.Builder builder = Point.measurement("BAS_ANALOG");
            builder.time(new Date().getTime()/1000,TimeUnit.SECONDS);
            builder.addField("value",analogMessage.getValue());
            builder.tag("line", analogMessage.getLineTag());
            builder.tag("region",analogMessage.getRegionTag());
            builder.tag("srcId","水泵系统");
            builder.tag("type","");  //Type未知
            builder.tag("pointcode",analogMessage.getPointcodeTag());
            Point point = builder.build() ;
            batchPoints.point(point);

        }

        BasInfluxDB.setDatabase("BAS").setRetentionPolicy("52w"). write(batchPoints);
    }

}
