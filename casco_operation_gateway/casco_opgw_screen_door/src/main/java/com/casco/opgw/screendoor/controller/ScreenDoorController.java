package com.casco.opgw.screendoor.controller;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.screendoor.dto.Constant;
import com.casco.opgw.screendoor.dto.Response;
import com.casco.opgw.screendoor.dto.alarm.AlarmDataDto;
import com.casco.opgw.screendoor.dto.alarm.AlarmDataItemDto;
import com.casco.opgw.screendoor.dto.analogdata.AnalogDataDto;
import com.casco.opgw.screendoor.dto.analogdata.AnalogDataItemDto;
import com.casco.opgw.screendoor.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Controller
@RequestMapping("/screendoor")
public class ScreenDoorController {

    @Autowired
    private KafkaService kafkaService;

    @RequestMapping(value = "/analogdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvAnalogData(@RequestBody AnalogDataDto analogDataDto) {
        Response response = new Response();
        //1. 数据异常校验
        if (null == analogDataDto.getAnalogdata()
                || analogDataDto.getAnalogdata().size() == 0) {

            response.setCode(Response.FAILED_CODE);
            response.setSuccess(false);
            return response;
        }

        AnalogMessage notification = new AnalogMessage();
        notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE); //通知消息
        if (analogDataDto.getType().equals(Constant.SIGNAL_TYPE_CHANGE)){
            for (AnalogDataItemDto item : analogDataDto.getAnalogdata()) {

                String[] keys = item.getKey().split("\\.");

                if (keys.length != Constant.SIGNAL_KEY_LENGTH_CHAGE) {
                    log.error("【SignalController】 recvAnalogData invalid key   " + item.getKey());
                    continue;
                }

                //BaseMessage数据 和屏蔽门数据文档数据有出入，这里暂时没处理！！！！！
                AnalogMessage analogMessage = new AnalogMessage();
                analogMessage.setMajor(keys[0]);
                analogMessage.setLineTag(keys[1]);
                analogMessage.setRegionTag(keys[2]);
                analogMessage.setSrcIdTag(keys[3]);
                analogMessage.setTypeTag(keys[4]);
                analogMessage.setPointcodeTag(keys[5]);


                analogMessage.setValue(item.getValue().get(0));
                analogMessage.setTimestamp(analogDataDto.getTimestamp() * 1000L + analogDataDto.getMs());
                analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                notification.getKeys().add(item.getKey());

                kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
            }
        }else{
            for (AnalogDataItemDto item : analogDataDto.getAnalogdata()) {

                String[] keys = item.getKey().split("\\.");

                if (keys.length != Constant.SIGNAL_KEY_LENGTH_ALL) {
                    log.error("【SignalController】 recvAnalogData invalid key   " + item.getKey());
                    continue;
                }

                for (int i = 0; i < item.getValue().size(); i++) {

                    AnalogMessage analogMessage = new AnalogMessage();
                    analogMessage.setMajor(keys[0]);
                    analogMessage.setLineTag(keys[1]);
                    analogMessage.setRegionTag(keys[2]);
                    analogMessage.setSrcIdTag(keys[3]);
                    analogMessage.setPointcodeTag(String.valueOf(i));
                    analogMessage.setTypeTag(keys[4]);

                    analogMessage.setValue(item.getValue().get(i));
                    analogMessage.setTimestamp(analogDataDto.getTimestamp() * 1000L + analogDataDto.getMs());
                    analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                    notification.getKeys().add(KeyUtils.getKey(analogMessage));

                    kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
                }
            }

    }
        kafkaService.sendAnalogMessage(JSON.toJSONString(notification));
        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);
        return response;
    }


    @RequestMapping(value = "/alarmdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvEnumData(@RequestBody AlarmDataDto alarmDataDto){
        Response response = new Response();

        for(AlarmDataItemDto item: alarmDataDto.getAlarmdata()){

            //取消使用signal包下AlarmMessage类,使用message包下的AlarmMessage
            AlarmMessage alarmMessage = new AlarmMessage();
            alarmMessage.setArmUuid(item.getUuid());
            alarmMessage.setArmDbm(item.getDbm());
            alarmMessage.setArmEquName(item.getEquname());
            alarmMessage.setArmEquType(item.getEqutype());
            alarmMessage.setArmSource(item.getSource());
            alarmMessage.setArmHappenTime(LocalDateTime.ofEpochSecond(Long.parseLong(item.getHappentime()),0, ZoneOffset.ofHours(8)));
            alarmMessage.setArmRestoreTime(LocalDateTime.ofEpochSecond(Long.parseLong(item.getRestoretime()),0, ZoneOffset.ofHours(8)));
            alarmMessage.setArmEquTypecode(Float.parseFloat(item.getEqutypecode()));
            alarmMessage.setArmContent(item.getContent());
            alarmMessage.setArmCode(Float.parseFloat(item.getCode()));
            alarmMessage.setArmLevel(Float.parseFloat(item.getLevel()));
            alarmMessage.setArmIsMaintain(Float.parseFloat(item.getIsmaintain()));
            alarmMessage.setArmEquCode(item.getEqucode());
            alarmMessage.setArmFaultBegin(Float.parseFloat(item.getFaultbegin()));
            alarmMessage.setArmFaultBegMs(Float.parseFloat(item.getFaultbegms()));
            alarmMessage.setArmFaultEnd(Float.parseFloat(item.getFaultend()));
            alarmMessage.setArmFaultEndMs(Float.parseFloat(item.getFaultendms()));
            alarmMessage.setMajor("屏蔽门维护数据");
            kafkaService.sendAlarmMessage(JSON.toJSONString(alarmMessage));
        }

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return  response;

    }

}
