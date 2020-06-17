package com.casco.opgw.kafkaalarm.task;

import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.kafkaalarm.BeanPorvider;
import com.casco.opgw.kafkaalarm.entity.SysAlarmTable;
import com.casco.opgw.kafkaalarm.mapper.SysAlarmTableMapper;
import org.springframework.beans.BeanUtils;

public class AlarmStoreTask implements Runnable{

    private SysAlarmTableMapper sysAlarmTableMapper;

    private AlarmMessage alarmMessage;

    public AlarmStoreTask(AlarmMessage alarmMessage){
        this.alarmMessage = alarmMessage;
    }

    @Override
    public void run() {

        sysAlarmTableMapper = BeanPorvider.getApplicationContext().getBean(SysAlarmTableMapper.class);

        SysAlarmTable sysAlarmTable = new SysAlarmTable();

        BeanUtils.copyProperties(alarmMessage, sysAlarmTable);

        SysAlarmTable message = sysAlarmTableMapper.selectById(sysAlarmTable.getArmUuid());

        if(message!=null){
            sysAlarmTableMapper.updateById(sysAlarmTable);
        }else{
            sysAlarmTableMapper.insert(sysAlarmTable);
        }

        message = null;

    }
}
