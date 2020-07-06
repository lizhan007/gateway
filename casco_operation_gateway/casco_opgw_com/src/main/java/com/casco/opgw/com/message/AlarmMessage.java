package com.casco.opgw.com.message;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AlarmMessage {
    private String armUuid;
    private String lineName;
    private String armEquType;
    private String armEquName;
    private String armContent;
    private String armEquCode;
    private String armAddEqu;
    private String armSource;
    private String armDbm;
    private LocalDateTime armHappenTime;
    private LocalDateTime armRestoreTime;
    private Float armFaultBegin;
    private Float armFaultBegMs;
    private Float armFaultEnd;
    private Float armFaultEndMs;
    private Float armCode;
    private Float armEquTypecode;
    private Float armLogicType1;
    private Float armLogicType2;
    private Float armLogicType3;
    private Float armLevel;
    private Float armIsMaintain;
    private Float armSaveNo;
    private String armAddJson;
    private String major;
}
