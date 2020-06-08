package com.casco.opgw.signal.dto.alarm;

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
}
