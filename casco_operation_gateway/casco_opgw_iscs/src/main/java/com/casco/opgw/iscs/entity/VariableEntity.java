package com.casco.opgw.iscs.entity;

import lombok.Data;

@Data
public class VariableEntity {

    private String equipType;  //对应excel表中设备类型
    private String varType;    //对应excel表中变量类型 DI或AI
    private String varName;    //对应excel表中变量名称
    private String ioType;     //对应excel表中IO类型
    private String port;       //对应excel表中modbus端口
    private String modbusAddr; //对应exlce表中modbus地址
    private String regAddr;    //针对bit类型数据，modbus中的bit位


}
