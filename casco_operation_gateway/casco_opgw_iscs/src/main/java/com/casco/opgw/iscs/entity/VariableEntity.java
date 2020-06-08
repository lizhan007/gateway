package com.casco.opgw.iscs.entity;


import lombok.Data;

@Data
public class VariableEntity {

    private String varName;
    private String port;
    private String modbusAddr;
    private String regAddr;
    private String devType;
    private String AttrTag;
    private String devPartition;

}
