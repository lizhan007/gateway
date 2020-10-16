package com.casco.opgw.screendoor.dto.analogdata;

import lombok.Data;

import java.util.List;

@Data
public class AnalogDataDto {
    private String type;
    private List<AnalogDataItemDto> analogdata;
    private Long timestamp;
    private Long ms;
}
