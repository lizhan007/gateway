package com.casco.opgw.alarmhandler.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casco.opgw.alarmhandler.BeanPorvider;
import com.casco.opgw.alarmhandler.config.InitTrainAlarmRule;
import com.casco.opgw.alarmhandler.config.model.TrainAlarmCfgModel;
import com.casco.opgw.alarmhandler.entity.SysAlarmTable;
import com.casco.opgw.alarmhandler.kafka.KafkaService;
import com.casco.opgw.alarmhandler.kafka.impl.KafkaServiceImpl;
import com.casco.opgw.alarmhandler.mapper.SysAlarmTableMapper;
import com.casco.opgw.alarmhandler.utils.IdUtils;
import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
public class TrainAlarmHandlerTask implements Runnable{

    private BaseMessage message;
    private KafkaService kafkaService;
    private SysAlarmTableMapper sysAlarmTableMapper;

    public TrainAlarmHandlerTask(BaseMessage baseMessage){
        this.message = baseMessage;
    }

    @Override
    public void run() {

        DigitMessage digitMessage = (DigitMessage)this.message;

        kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
            //通知类消息不做处理
            return;
        }

        //1. 根据报警定义信息判断
        TrainAlarmCfgModel target = null;

        for(TrainAlarmCfgModel model: InitTrainAlarmRule.trainAlarmCfgModelList){
            if(model.getVarName().equals(digitMessage.getPointcodeTag())){
                target = model;
                break;
            }
        }

        if(null == target){
            //log.info("【TrainAlarmHandlerTask】 msg without alarm cfg : " + digitMessage.getPointcodeTag());
            return;
        }
        //2. 检修逻辑处理
        //2.1 检修状态下过滤，非检修通知类消息
        if(InitTrainAlarmRule.getIsOverHaul() == 1
        && !digitMessage.getPointcodeTag().contains("_OVERHAUL_MODE_1")){
            //在检修状态下，除非是 OVERHAUL_MODE 检修消息，其他不做处理
            //log.info("【TrainAlarmHandlerTask】 msg without alarm cfg : " + digitMessage.getPointcodeTag());
            return;
        }

        //2.2 更新本地检修状态
        if(digitMessage.getPointcodeTag().contains("_OVERHAUL_MODE_1")){

            log.warn("【TrainAlarmHandlerTask】 OverHaul update： " + digitMessage.getValue());
            InitTrainAlarmRule.setIsOverHaul(digitMessage.getValue());
        }


        //2. 判断是否是告警信息
        if(digitMessage.getValue() == 1){
            //2.1 当前消息为告警，则新增一条告警数据
            AlarmMessage message = new AlarmMessage();
            message.setLineName(target.getLine());
            message.setArmUuid(IdUtils.createUUID());
            message.setArmEquType("车辆");
            message.setArmEquName(target.getTrain());
            message.setArmContent(target.getAlarmContent());
            message.setArmEquCode(target.getTrain());
            message.setArmSource("车辆专家系统");
            message.setArmDbm(target.getTrain());
            message.setArmFaultBegin(Float.parseFloat(digitMessage.getTimestamp().toString()));
            message.setArmFaultEnd(Float.parseFloat(digitMessage.getTimestamp().toString()));
            message.setArmAddEqu(digitMessage.getPointcodeTag());
            message.setArmLevel(Float.valueOf(target.getAlarmLevel()));
            message.setArmHappenTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(digitMessage.getTimestamp()),
                    ZoneId.systemDefault()));
            message.setMajor("车辆专业");
            message.setArmAddJson(target.getVarName());
            kafkaService.sendTrainAlarmMessage(JSON.toJSONString(message));

            //2.2 更新当前检测值缓存
            InitTrainAlarmRule.trainCache.put(digitMessage.getPointcodeTag(), digitMessage.getValue());

        }else{
            //2.2 当前消息为无告警，则新增一条告警撤销数据
            //逻辑：根据设备 + 码 查询所有的未回复告警，添加告警恢复
            sysAlarmTableMapper = BeanPorvider.getApplicationContext().getBean(SysAlarmTableMapper.class);

            if(1 == (int)InitTrainAlarmRule.trainCache.get(digitMessage.getPointcodeTag())
            || !InitTrainAlarmRule.trainCache.contains(digitMessage.getPointcodeTag())){

                //处理缓存为【告警】，或者缓存不存在，后者主要针对重启后第一条消息的情况


                LambdaQueryWrapper<SysAlarmTable> queryWrapper
                        =new QueryWrapper<SysAlarmTable>().lambda()
                        .eq(SysAlarmTable::getLineName,target.getLine())
                        .eq(SysAlarmTable::getArmEquName, target.getTrain())
                        .eq(SysAlarmTable::getArmAddEqu, target.getVarName())
                        .isNull(SysAlarmTable::getArmRestoreTime);

                List<SysAlarmTable> alarmTableList =
                         sysAlarmTableMapper.selectList(queryWrapper);

                LocalDateTime current = LocalDateTime.now();

                for(SysAlarmTable table: alarmTableList){

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
                    message.setArmAddJson(table.getArmAddJson());
                    message.setArmFaultBegin(table.getArmFaultBegin());
                    message.setArmFaultEnd(table.getArmFaultEnd());
                    message.setArmLevel(table.getArmLevel());
                    message.setMajor(table.getMajor());
                    kafkaService.sendTrainAlarmMessage(JSON.toJSONString(message));

                    InitTrainAlarmRule.trainCache.put(digitMessage.getPointcodeTag(), digitMessage.getValue());
                }
            }
        }//}else{
    }
}
