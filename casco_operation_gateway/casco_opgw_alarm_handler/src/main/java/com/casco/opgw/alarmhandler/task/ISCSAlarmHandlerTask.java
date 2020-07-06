package com.casco.opgw.alarmhandler.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casco.opgw.alarmhandler.BeanPorvider;
import com.casco.opgw.alarmhandler.config.InitISCSAlarmRule;
import com.casco.opgw.alarmhandler.config.InitTrainAlarmRule;
import com.casco.opgw.alarmhandler.config.model.ISCSAlarmCfgModel;
import com.casco.opgw.alarmhandler.entity.SysAlarmTable;
import com.casco.opgw.alarmhandler.kafka.KafkaService;
import com.casco.opgw.alarmhandler.kafka.impl.KafkaServiceImpl;
import com.casco.opgw.alarmhandler.mapper.SysAlarmTableMapper;
import com.casco.opgw.alarmhandler.utils.IdUtils;
import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
public class ISCSAlarmHandlerTask implements Runnable{

    private BaseMessage message;
    private KafkaService kafkaService;
    private SysAlarmTableMapper sysAlarmTableMapper;

    public ISCSAlarmHandlerTask(BaseMessage baseMessage){
        this.message = baseMessage;
    }

    @SneakyThrows
    @Override
    public void run() {
        DigitMessage digitMessage = (DigitMessage)this.message;
        kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            //通知类消息不做处理
            return;
        }
        //1. 根据报警信息判断
        ISCSAlarmCfgModel target = null;

        for(ISCSAlarmCfgModel item: InitISCSAlarmRule.iscsAlarmCfgModelList){
            if(item.getVarName().equals(digitMessage.getPointcodeTag())){
                target = item;
                break;
            }
        }

        if(null == target){
            //log.info("【ISCSAlarmHandlerTask】 msg without alarm cfg : " + digitMessage.getPointcodeTag());
            return;
        }
        //2. 判断报警信息
        if(digitMessage.getValue() == 1){
            AlarmMessage message = new AlarmMessage();
            message.setLineName(target.getLine());
            message.setArmUuid(IdUtils.createUUID());
            message.setArmEquType(target.getEquipType());
            message.setArmEquName(target.getEquipLocation());
            message.setArmContent(target.getAlarmContent());
            message.setArmEquCode(target.getEquipCode());
            message.setArmSource("水泵系统");
            message.setArmDbm(target.getStation());
            message.setArmFaultBegin(Float.parseFloat(digitMessage.getTimestamp().toString()));
            message.setArmFaultEnd(Float.parseFloat(digitMessage.getTimestamp().toString()));
            message.setArmAddEqu(digitMessage.getPointcodeTag());
            //message.setArmCode(Float.valueOf(target.getEquipType()));
            message.setArmLevel(Float.valueOf(target.getAlarmLevel()));
            message.setArmHappenTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(digitMessage.getTimestamp()),
                    ZoneId.systemDefault()));
            kafkaService.sendSCSIAlarmMessage(JSON.toJSONString(message));
            InitISCSAlarmRule.iscsCache.put(digitMessage.getPointcodeTag(), digitMessage.getValue());

        }else{

            sysAlarmTableMapper = BeanPorvider.getApplicationContext().getBean(SysAlarmTableMapper.class);

            if(1 == (int)InitISCSAlarmRule.iscsCache.get(digitMessage.getPointcodeTag())
            || !InitISCSAlarmRule.iscsCache.contains(digitMessage.getPointcodeTag())){
                //处理缓存为【告警】，或者缓存不存在，后者主要针对重启后第一条消息的情况

                LambdaQueryWrapper<SysAlarmTable> queryWrapper
                        =new QueryWrapper<SysAlarmTable>().lambda()
                        .eq(SysAlarmTable::getLineName,target.getLine())
                        .eq(SysAlarmTable::getArmEquName, target.getEquipLocation())
                        .eq(SysAlarmTable::getArmAddEqu, target.getVarName()) //附加设备字段存储pointTag
                        .isNull(SysAlarmTable::getArmRestoreTime);
                List<SysAlarmTable> alarmTableList =
                        sysAlarmTableMapper.selectList(queryWrapper);

                LocalDateTime current = LocalDateTime.now();

                for(SysAlarmTable table:alarmTableList){
                    AlarmMessage message = new AlarmMessage();
                    message.setArmUuid(table.getArmUuid());
                    message.setLineName(table.getLineName());
                    message.setArmEquType(table.getArmEquType());
                    message.setArmEquName(table.getArmEquName());
                    message.setArmContent(table.getArmContent());
                    message.setArmEquCode(table.getArmEquCode());
                    message.setArmSource(table.getArmSource());
                    message.setArmDbm(table.getArmDbm());
                    message.setArmLevel(table.getArmLevel());
                    message.setArmHappenTime(table.getArmHappenTime());
                    message.setArmRestoreTime(current);
                    message.setArmAddEqu(table.getArmAddEqu());
                    message.setArmFaultBegin(table.getArmFaultBegin());
                    message.setArmFaultEnd(table.getArmFaultEnd());

                    message.setArmAddJson(table.getArmAddJson());
                    kafkaService.sendSCSIAlarmMessage(JSON.toJSONString(message));
                    InitISCSAlarmRule.iscsCache.put(digitMessage.getPointcodeTag(), digitMessage.getValue());
                }
            }
        }//}else{
    }
}
