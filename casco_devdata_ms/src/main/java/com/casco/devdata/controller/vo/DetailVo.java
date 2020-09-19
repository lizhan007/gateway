package com.casco.devdata.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class DetailVo {

    String devId;
    String devName;
    List<String> types;
    int start;
    int limit;
}
