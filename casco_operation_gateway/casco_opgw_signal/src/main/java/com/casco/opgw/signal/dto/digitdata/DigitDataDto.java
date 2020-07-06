package com.casco.opgw.signal.dto.digitdata;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DigitDataDto {
    private String type;
    private List<DigitDataItemDto> digitdata;
    private Long timestamp;
    private Long ms;
}
