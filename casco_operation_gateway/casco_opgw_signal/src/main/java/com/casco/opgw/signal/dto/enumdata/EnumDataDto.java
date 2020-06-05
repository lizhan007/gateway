package com.casco.opgw.signal.dto.enumdata;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class EnumDataDto {
    private String type;
    private List<EnumDataItemDto> enumdata;
    private Long timestamp;
}
