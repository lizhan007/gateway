package com.casco.opgw.signal.controller;

import com.casco.opgw.com.message.AlarmMessage;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.signal.dto.Constant;
import com.casco.opgw.signal.dto.Response;
import com.casco.opgw.signal.dto.alarm.AlarmDataDto;
import com.casco.opgw.signal.dto.alarm.AlarmDataItemDto;
import com.casco.opgw.signal.dto.analogdata.AnalogDataDto;
import com.casco.opgw.signal.dto.analogdata.AnalogDataItemDto;
import com.casco.opgw.signal.dto.digitdata.DigitDataDto;
import com.casco.opgw.signal.dto.digitdata.DigitDataItemDto;
import com.casco.opgw.signal.dto.enumdata.EnumDataDto;
import com.casco.opgw.signal.dto.enumdata.EnumDataItemDto;
import com.casco.opgw.signal.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/transmission")
public class SignalController {

    @Autowired
    private KafkaService kafkaService;

    @RequestMapping(value = "/digitdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvDigitData(@RequestBody DigitDataDto digitDataDto){

        log.info(digitDataDto.toString());

        System.out.println(digitDataDto.getType() + " size: " +
                digitDataDto.getDigitdata().size() + "  " + new Date(digitDataDto.getTimestamp()*1000));

        Response response = new Response();

        //1. 数据异常校验
        if(null == digitDataDto.getDigitdata()
                || digitDataDto.getDigitdata().size() == 0){

            response.setCode(Response.FAILED_CODE);
            response.setSuccess(false);
            return  response;
        }

        DigitMessage notification = new DigitMessage();
        notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE); //通知消息

        if(digitDataDto.getType().equals(Constant.SIGNAL_TYPE_CHANGE)){

            for(DigitDataItemDto item : digitDataDto.getDigitdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_CHAGE){
                    log.error("【SignalController】 recvDigitData invalid key   " + item.getKey());
                    continue;
                }

                DigitMessage digitMessage = new DigitMessage();
                digitMessage.setMajor(keys[0]);
                digitMessage.setLineTag(keys[1]);
                digitMessage.setRegionTag(keys[2]);
                digitMessage.setSrcIdTag(keys[3]);
                digitMessage.setTypeTag(keys[4]);
                digitMessage.setPointcodeTag(keys[5]);

                digitMessage.setValue(item.getValue().get(0));
                digitMessage.setTimestamp(digitDataDto.getTimestamp());
                digitMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                notification.getKeys().add(item.getKey());

                kafkaService.sendDigitMessage(JSON.toJSONString(digitMessage));
            }
        }else{

            for(DigitDataItemDto item : digitDataDto.getDigitdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_ALL){
                    log.error("【SignalController】 invalid key   " + item.getKey());
                    continue;
                }

                for(int i = 0; i < item.getValue().size(); i++){

                    DigitMessage digitMessage = new DigitMessage();
                    digitMessage.setMajor(keys[0]);
                    digitMessage.setLineTag(keys[1]);
                    digitMessage.setRegionTag(keys[2]);
                    digitMessage.setSrcIdTag(keys[3]);
                    digitMessage.setPointcodeTag(String.valueOf(i));
                    digitMessage.setTypeTag(keys[4]);

                    digitMessage.setValue(item.getValue().get(i));
                    digitMessage.setTimestamp(digitDataDto.getTimestamp());
                    digitMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                    notification.getKeys().add(KeyUtils.getKey(digitMessage));

                    kafkaService.sendDigitMessage(JSON.toJSONString(digitMessage));
                }
            }
        }

        kafkaService.sendDigitMessage(JSON.toJSONString(notification));

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return  response;
    }


    @RequestMapping(value = "/enumdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvEnumData(@RequestBody EnumDataDto enumDataDto){

        log.info(enumDataDto.toString());

        System.out.println(enumDataDto.getType() + " size: " +
                enumDataDto.getEnumdata().size() + "  " + new Date(enumDataDto.getTimestamp()*1000));

        Response response = new Response();

        //1. 数据异常校验
        if(null == enumDataDto.getEnumdata()
                || enumDataDto.getEnumdata().size() == 0){

            response.setCode(Response.FAILED_CODE);
            response.setSuccess(false);
            return  response;
        }

        EnumMessage notification = new EnumMessage();
        notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE); //通知消息

        if(enumDataDto.getType().equals(Constant.SIGNAL_TYPE_CHANGE)){

            for(EnumDataItemDto item : enumDataDto.getEnumdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_CHAGE){
                    log.error("【SignalController】 recvEnumData invalid key   " + item.getKey());
                    continue;
                }

                EnumMessage enumMessage = new EnumMessage();
                enumMessage.setMajor(keys[0]);
                enumMessage.setLineTag(keys[1]);
                enumMessage.setRegionTag(keys[2]);
                enumMessage.setSrcIdTag(keys[3]);
                enumMessage.setTypeTag(keys[4]);
                enumMessage.setPointcodeTag(keys[5]);


                enumMessage.setValue(item.getValue().get(0));
                enumMessage.setTimestamp(enumDataDto.getTimestamp());
                enumMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                notification.getKeys().add(item.getKey());

                kafkaService.sendEnumMessage(JSON.toJSONString(enumMessage));
            }
        }else{

            for(EnumDataItemDto item : enumDataDto.getEnumdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_ALL){
                    log.error("【SignalController】 recvEnumData invalid key   " + item.getKey());
                    continue;
                }

                for(int i = 0; i < item.getValue().size(); i++){

                    EnumMessage enumMessage = new EnumMessage();
                    enumMessage.setMajor(keys[0]);
                    enumMessage.setLineTag(keys[1]);
                    enumMessage.setRegionTag(keys[2]);
                    enumMessage.setSrcIdTag(keys[3]);
                    enumMessage.setPointcodeTag(String.valueOf(i));
                    enumMessage.setTypeTag(keys[4]);

                    enumMessage.setValue(item.getValue().get(i));
                    enumMessage.setTimestamp(enumDataDto.getTimestamp());
                    enumMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);



                    notification.getKeys().add(KeyUtils.getKey(enumMessage));

                    kafkaService.sendEnumMessage(JSON.toJSONString(enumMessage));
                }
            }
        }

        kafkaService.sendEnumMessage(JSON.toJSONString(notification));

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return  response;
    }

    @RequestMapping(value = "/analogdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvAnalogData(@RequestBody AnalogDataDto analogDataDto){

        System.out.println(analogDataDto.toString());

        Response response = new Response();

        //1. 数据异常校验
        if(null == analogDataDto.getAnalogdata()
                || analogDataDto.getAnalogdata().size() == 0){

            response.setCode(Response.FAILED_CODE);
            response.setSuccess(false);
            return  response;
        }

        AnalogMessage notification = new AnalogMessage();
        notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE); //通知消息


        if(analogDataDto.getType().equals(Constant.SIGNAL_TYPE_CHANGE)){

            for(AnalogDataItemDto item : analogDataDto.getAnalogdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_CHAGE){
                    log.error("【SignalController】 recvAnalogData invalid key   " + item.getKey());
                    continue;
                }

                AnalogMessage analogMessage = new AnalogMessage();
                analogMessage.setMajor(keys[0]);
                analogMessage.setLineTag(keys[1]);
                analogMessage.setRegionTag(keys[2]);
                analogMessage.setSrcIdTag(keys[3]);
                analogMessage.setTypeTag(keys[4]);
                analogMessage.setPointcodeTag(keys[5]);


                analogMessage.setValue(item.getValue().get(0));
                analogMessage.setTimestamp(analogDataDto.getTimestamp());
                analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                notification.getKeys().add(item.getKey());

                kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
            }
        }else{

            for(AnalogDataItemDto item : analogDataDto.getAnalogdata()){

                String[] keys = item.getKey().split("\\.");

                if(keys.length != Constant.SIGNAL_KEY_LENGTH_ALL){
                    log.error("【SignalController】 recvAnalogData invalid key   " + item.getKey());
                    continue;
                }

                for(int i = 0; i < item.getValue().size(); i++){

                    AnalogMessage analogMessage = new AnalogMessage();
                    analogMessage.setMajor(keys[0]);
                    analogMessage.setLineTag(keys[1]);
                    analogMessage.setRegionTag(keys[2]);
                    analogMessage.setSrcIdTag(keys[3]);
                    analogMessage.setPointcodeTag(String.valueOf(i ));
                    analogMessage.setTypeTag(keys[4]);

                    analogMessage.setValue(item.getValue().get(i));
                    analogMessage.setTimestamp(analogDataDto.getTimestamp());
                    analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);

                    notification.getKeys().add(KeyUtils.getKey(analogMessage));

                    kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
                }
            }
        }

        kafkaService.sendEnumMessage(JSON.toJSONString(notification));

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return  response;
    }


    @RequestMapping(value = "/alarmdata", method = RequestMethod.POST)
    @ResponseBody
    public Response recvEnumData(@RequestBody AlarmDataDto alarmDataDto){

        System.out.println(alarmDataDto.toString());

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
            //BeanUtils.copyProperties(item, alarmMessage);

            kafkaService.sendAlarmMessage(JSON.toJSONString(alarmMessage));
        }

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return  response;

    }






}
