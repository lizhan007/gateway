package com.casco.opgw.signal.dto.enumdata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class EnumDataItemDto {
    private String key;
    private List<Integer> value = new ArrayList<>();
}
