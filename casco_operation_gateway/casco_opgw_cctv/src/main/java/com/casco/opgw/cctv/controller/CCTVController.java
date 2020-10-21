package com.casco.opgw.cctv.controller;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.cctv.dto.Response;
import com.casco.opgw.cctv.dto.alarm.AlarmDataDto;
import com.casco.opgw.cctv.dto.alarm.AlarmDataItemDto;
import com.casco.opgw.cctv.dto.alarm.CCTVStateDto;
import com.casco.opgw.cctv.kafka.KafkaService;
import com.casco.opgw.com.message.AlarmMessage;
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
@RequestMapping("/cctv")
public class CCTVController {

    @Autowired
    private KafkaService kafkaService;

    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @ResponseBody
    public Response recvEnumData(@RequestBody AlarmDataDto alarmDataDto){
        Response response = new Response();
        log.info(alarmDataDto.toString());
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
//            alarmMessage.setArmEquTypecode(Float.parseFloat(item.getEqutypecode()));
            alarmMessage.setArmContent(item.getContent());
            //alarmMessage.setArmCode(Float.parseFloat(item.getCode()));
            alarmMessage.setArmLevel(Float.parseFloat(item.getLevel()));
            alarmMessage.setArmIsMaintain(Float.parseFloat(item.getIsmaintain()));
            alarmMessage.setArmEquCode(item.getEqucode());
            alarmMessage.setArmFaultBegin(Float.parseFloat(item.getFaultbegin()));
            alarmMessage.setArmFaultBegMs(Float.parseFloat(item.getFaultbegms()));
            alarmMessage.setArmFaultEnd(Float.parseFloat(item.getFaultend()));
            alarmMessage.setArmFaultEndMs(Float.parseFloat(item.getFaultendms()));
            alarmMessage.setMajor("CCTV维护数据");
            kafkaService.sendAlarmMessage(JSON.toJSONString(alarmMessage));
        }

        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);

        return response;

    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Response recvStateData(@RequestBody CCTVStateDto cctvStateDto) {
        Response response = new Response();
        //待kafka接收端完成后 修改为接收端格式数据
        kafkaService.sendStateMessage(JSON.toJSONString(cctvStateDto));
        response.setCode(Response.SUCCESS_CODE);
        response.setSuccess(true);
        return response;
    }
}
