package com.casco.opgw.kafkaalarm.kafka;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.kafkaalarm.BeanPorvider;
import com.casco.opgw.kafkaalarm.entity.AdsAlarmCountTable;
import com.casco.opgw.kafkaalarm.entity.SysAlarmTable;
import com.casco.opgw.kafkaalarm.mapper.AdsAlarmCountTableMapper;
import com.casco.opgw.kafkaalarm.mapper.SysAlarmTableMapper;
import com.casco.opgw.kafkaalarm.redis.AnalogRedisUtils;
import com.casco.opgw.kafkaalarm.task.AlarmStoreTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumer {

    @Resource(name = "alarmStoreToMysqlExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;

    public static AdsAlarmCountTable adsAlarmCountTable;

    /**
     * 加载AdsAlarmCount数据
     */
    public static void initAdsAlarmCount(){
        if(adsAlarmCountTable==null){
            //AdsAlarmCountTableMapper adsAlarmCountTableMapper = BeanPorvider.getApplicationContext().getBean(AdsAlarmCountTableMapper.class);
            SysAlarmTableMapper sysAlarmTableMapper = BeanPorvider.getApplicationContext().getBean(SysAlarmTableMapper.class);
            adsAlarmCountTable = new AdsAlarmCountTable();
            LambdaQueryWrapper<SysAlarmTable> queryWrapper
                    =new QueryWrapper<SysAlarmTable>().lambda()
                    .in(SysAlarmTable::getArmLevel,1,2)
                    .isNull(SysAlarmTable::getArmRestoreTime);
            adsAlarmCountTable.setAlarmCount(sysAlarmTableMapper.selectCount(queryWrapper));

            queryWrapper
                    =new QueryWrapper<SysAlarmTable>().lambda()
                    .notIn(SysAlarmTable::getArmLevel,1,2)
                    .isNull(SysAlarmTable::getArmRestoreTime);
            adsAlarmCountTable.setEarlyAlarmCount(sysAlarmTableMapper.selectCount(queryWrapper));
        }
    }


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
