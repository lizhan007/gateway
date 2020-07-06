package com.casco.opgw.kafkaalarm.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.kafkaalarm.task.AlarmStoreTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;



@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumer {

    @Resource(name = "alarmStoreToMysqlExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;


    @KafkaListener(topics = "casco_opgw_signal_alarm", groupId = "casco_opgw_kafka_alarm")
    public void recvAlarmMsg(ConsumerRecord<String, String> consumerRecord){

        AlarmMessage alarmMessage = JSON.parseObject(consumerRecord.value(), AlarmMessage.class);

        //1. 时间戳
        //2. uuid 主键 restoretime

        poolTaskExecutor.submit(new AlarmStoreTask(alarmMessage));
    }

    @KafkaListener(topics = "casco_opgw_train_alarm", groupId = "casco_opgw_kafka_alarm")
    public void recvTrainAlarmMsg(ConsumerRecord<String, String> consumerRecord){

        AlarmMessage alarmMessage = JSON.parseObject(consumerRecord.value(), AlarmMessage.class);

        //1. 时间戳
        //2. uuid 主键 restoretime

        poolTaskExecutor.submit(new AlarmStoreTask(alarmMessage));
    }

    @KafkaListener(topics = "casco_opgw_iscs_alarm", groupId = "casco_opgw_kafka_alarm")
    public void recvISCSAlarmMsg(ConsumerRecord<String, String> consumerRecord){

        AlarmMessage alarmMessage = JSON.parseObject(consumerRecord.value(), AlarmMessage.class);

        //1. 时间戳
        //2. uuid 主键 restoretime

        poolTaskExecutor.submit(new AlarmStoreTask(alarmMessage));
    }
}
