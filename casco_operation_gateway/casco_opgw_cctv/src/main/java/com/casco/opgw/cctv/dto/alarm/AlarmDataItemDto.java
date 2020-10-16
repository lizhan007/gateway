package com.casco.opgw.cctv.dto.alarm;

import lombok.Data;

@Data
public class AlarmDataItemDto {
    private String uuid;
    private String dbm;
    private String equname;
    private String equtype;
    private String source;
    private String happentime;
    private String restoretime;
    private String equtypecode;
    private String content;
    private String code;
    private String level;
    private String ismaintain;
    private String equcode;
    private String faultbegin;
    private String faultbegms;
    private String faultend;
    private String faultendms;
}
