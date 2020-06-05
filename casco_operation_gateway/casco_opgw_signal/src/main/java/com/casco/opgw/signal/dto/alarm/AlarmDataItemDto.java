package com.casco.opgw.signal.dto.alarm;

import lombok.Data;

@Data
public class AlarmDataItemDto {
    private String uuid;
    private String dbm;
    private String devname;
    private String happentime;
    private String restoretime;
    private String devtypecode;
    private String content;
    private String alarmcode;
}
