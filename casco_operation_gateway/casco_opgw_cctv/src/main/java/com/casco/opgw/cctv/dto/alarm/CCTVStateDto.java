package com.casco.opgw.cctv.dto.alarm;

import lombok.Data;

import java.util.List;

@Data
public class CCTVStateDto {
    private String type;
    private List<CCTVStateItemDto> data;
}
