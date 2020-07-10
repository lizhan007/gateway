package com.casco.siganalysis.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ListAlarmVo {

    private List<String> alarmCode;
    private String start;
    private String end;

}
