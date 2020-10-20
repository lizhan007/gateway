package com.casco.opgw.cctv.dto.alarm;

import lombok.Data;

import java.util.List;

@Data
public class CCTVDataDto {
    private String type;
    private List<CCTVDataItemDto> data;
}
