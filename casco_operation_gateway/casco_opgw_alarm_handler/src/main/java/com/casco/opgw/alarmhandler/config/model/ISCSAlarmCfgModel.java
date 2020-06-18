package com.casco.opgw.alarmhandler.config.model;

import lombok.Data;

@Data
public class ISCSAlarmCfgModel {

    private String line;
    private String station;
    private String equipType;
    private String equipLocation;
    private String alarmContent; //对应 Property Description
    private String alarmLevel;
    private String varName;
    private String equipCode;
}
