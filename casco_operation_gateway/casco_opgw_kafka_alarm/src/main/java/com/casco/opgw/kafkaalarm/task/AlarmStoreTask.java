package com.casco.opgw.kafkaalarm.task;

import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.kafkaalarm.BeanPorvider;
import com.casco.opgw.kafkaalarm.entity.SysAlarmTable;
import com.casco.opgw.kafkaalarm.kafka.KafkaConsumer;
import com.casco.opgw.kafkaalarm.mapper.AdsAlarmCountTableMapper;
import com.casco.opgw.kafkaalarm.mapper.SysAlarmTableMapper;
import com.casco.opgw.kafkaalarm.redis.AnalogRedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AlarmStoreTask implements Runnable{

    private SysAlarmTableMapper sysAlarmTableMapper;

    private AdsAlarmCountTableMapper adsAlarmCountTableMapper;

    private AlarmMessage alarmMessage;

    private AnalogRedisUtils analogRedisUtils;

    public AlarmStoreTask(AlarmMessage alarmMessage){
        this.alarmMessage = alarmMessage;
    }

    @Override
    public void run() {

        sysAlarmTableMapper = BeanPorvider.getApplicationContext().getBean(SysAlarmTableMapper.class);

        adsAlarmCountTableMapper = BeanPorvider.getApplicationContext().getBean(AdsAlarmCountTableMapper.class);

        analogRedisUtils = BeanPorvider.getApplicationContext().getBean(AnalogRedisUtils.class);

        //KafkaConsumer.initAdsAlarmCount();

        SysAlarmTable sysAlarmTable = new SysAlarmTable();

        BeanUtils.copyProperties(alarmMessage, sysAlarmTable);

        SysAlarmTable message = sysAlarmTableMapper.selectById(sysAlarmTable.getArmUuid());


        if(message != null){
/*            if(sysAlarmTable.getArmLevel()==1||sysAlarmTable.getArmLevel()==2){
                KafkaConsumer.adsAlarmCountTable.setAlarmCount(KafkaConsumer.adsAlarmCountTable.getAlarmCount()-1);
                analogRedisUtils.set("alarm_count",String.valueOf(KafkaConsumer.adsAlarmCountTable.getAlarmCount()));
            }else{
                KafkaConsumer.adsAlarmCountTable.setEarlyAlarmCount(KafkaConsumer.adsAlarmCountTable.getEarlyAlarmCount()-1);
                analogRedisUtils.set("early_alarm_count",String.valueOf(KafkaConsumer.adsAlarmCountTable.getEarlyAlarmCount()));
            }*/
            sysAlarmTableMapper.updateById(sysAlarmTable);
        }else{
/*            if(sysAlarmTable.getArmLevel()==1||sysAlarmTable.getArmLevel()==2){
                KafkaConsumer.adsAlarmCountTable.setAlarmCount(KafkaConsumer.adsAlarmCountTable.getAlarmCount()+1);
                analogRedisUtils.set("alarm_count",String.valueOf(KafkaConsumer.adsAlarmCountTable.getAlarmCount()));
            }else{
                KafkaConsumer.adsAlarmCountTable.setEarlyAlarmCount(KafkaConsumer.adsAlarmCountTable.getEarlyAlarmCount()+1);
                analogRedisUtils.set("early_alarm_count",String.valueOf(KafkaConsumer.adsAlarmCountTable.getEarlyAlarmCount()));
            }*/
            sysAlarmTableMapper.insert(sysAlarmTable);
        }
        message = null;
        //adsAlarmCountTableMapper.update(KafkaConsumer.adsAlarmCountTable,null);
    }
}
