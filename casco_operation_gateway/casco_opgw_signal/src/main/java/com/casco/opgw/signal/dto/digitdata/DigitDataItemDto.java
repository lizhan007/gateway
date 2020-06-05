package com.casco.opgw.signal.dto.digitdata;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DigitDataItemDto {

    private String key;
    private List<Integer> value = new ArrayList<>();
}
