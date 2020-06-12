package com.casco.opgw.alarmhandler.config.model;


import lombok.Data;

@Data
public class TrainAlarmCfgModel {

    private String line;
    private String train;
    private String varName;
    private String equipType;
    private String alarmContent; //对应属性列
    private String alarmLevel;


}
