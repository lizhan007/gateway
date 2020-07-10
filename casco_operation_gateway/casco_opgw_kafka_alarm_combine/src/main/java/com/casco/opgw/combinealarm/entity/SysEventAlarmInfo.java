package com.casco.opgw.combinealarm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_event_alarm_info")
public class SysEventAlarmInfo extends Model<SysEventAlarmInfo> {

    @TableId("id")
    private String id;

    @TableField("event_type_id")
    private String eventTypeId;

    @TableField("arm_veh_code")
    private String armVehCode;

    @TableField("arm_code_value")
    private Integer armCodeValue;

    @TableField("arm_content")
    private String armContent;

    @TableField("arm_dbm")
    private String armDbm;

    @TableField("arm_source")
    private String armSource;

    @TableField("arm_equ_name")
    private String armEquName;

    @TableField("arm_equ_code")
    private String armEquCode;

    @TableField("arm_equ_type")
    private String armEquType;

    @TableField("arm_level")
    private Float armLevel;

    @TableField("arm_equ_type_code")
    private Float armEquTypeCode;

    @TableField("arm_code")
    private Float armCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getArmVehCode() {
        return armVehCode;
    }

    public void setArmVehCode(String armVehCode) {
        this.armVehCode = armVehCode;
    }

    public Integer getArmCodeValue() {
        return armCodeValue;
    }

    public void setArmCodeValue(Integer armCodeValue) {
        this.armCodeValue = armCodeValue;
    }

    public String getArmContent() {
        return armContent;
    }

    public void setArmContent(String armContent) {
        this.armContent = armContent;
    }

    public String getArmDbm() {
        return armDbm;
    }

    public void setArmDbm(String armDbm) {
        this.armDbm = armDbm;
    }

    public String getArmSource() {
        return armSource;
    }

    public void setArmSource(String armSource) {
        this.armSource = armSource;
    }

    public String getArmEquName() {
        return armEquName;
    }

    public void setArmEquName(String armEquName) {
        this.armEquName = armEquName;
    }

    public String getArmEquCode() {
        return armEquCode;
    }

    public void setArmEquCode(String armEquCode) {
        this.armEquCode = armEquCode;
    }

    public String getArmEquType() {
        return armEquType;
    }

    public void setArmEquType(String armEquType) {
        this.armEquType = armEquType;
    }

    public Float getArmLevel() {
        return armLevel;
    }

    public void setArmLevel(Float armLevel) {
        this.armLevel = armLevel;
    }

    public Float getArmEquTypeCode() {
        return armEquTypeCode;
    }

    public void setArmEquTypeCode(Float armEquTypeCode) {
        this.armEquTypeCode = armEquTypeCode;
    }

    public Float getArmCode() {
        return armCode;
    }

    public void setArmCode(Float armCode) {
        this.armCode = armCode;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
