package com.casco.devdata.controller.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysDevMainTypeDefVo {

    private String devMainTypeId;


    private String devMainTypeName;

    private String major;

    List<SysDevTypeDefVo> sysDevTypeDefVoList = new ArrayList<>();
}
