package com.casco.opgw.com.message.signal;

import lombok.Data;

@Data
public class AlarmMessage{
    private String uuid;
    private String dbm;
    private String devname;
    private String happentime;
    private String restoretime;
    private String devtypecode;
    private String content;
    private String alarmcode;
}
