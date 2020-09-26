package com.casco.opgw.alarmhandler.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.alarmhandler.task.ISCSAlarmHandlerTask;
import com.casco.opgw.alarmhandler.task.TrainAlarmHandlerTask;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class KafkaConsumer {

    @Resource(name = "alarmHandlerExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;

    /*-
     * 当前的告警处理都是0/1的数字量
     */

    @KafkaListener(topics = "casco_opgw_train_digit", groupId = "casco_opgw_alarm_handler")
    public void recvTrainDigitMessage(ConsumerRecord<String, String> consumerRecord){
        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);
        poolTaskExecutor.submit(new TrainAlarmHandlerTask(digitMessage));
    }

    @KafkaListener(topics = "casco_opgw_iscs_digit", groupId = "casco_opgw_alarm_handler")
    public void recvISCSDigitMessage(ConsumerRecord<String, String> consumerRecord){
        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);
        poolTaskExecutor.submit(new ISCSAlarmHandlerTask(digitMessage));
    }
}
