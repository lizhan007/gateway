package com.casco.opgw.signal.dto.analogdata;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AnalogDataDto {
    private String type;
    private List<AnalogDataItemDto> analogdata;
    private Long timestamp;
}
