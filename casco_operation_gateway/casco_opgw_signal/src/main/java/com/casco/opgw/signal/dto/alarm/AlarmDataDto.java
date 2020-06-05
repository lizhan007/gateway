package com.casco.opgw.signal.dto.alarm;

import lombok.Data;

import java.util.List;

@Data
public class AlarmDataDto {

    private List<AlarmDataItemDto> alarmdata;
}
