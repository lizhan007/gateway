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
@TableName("sys_event_info")
public class SysEventInfo extends Model<SysEventInfo> {

    @TableId("id")
    private String id;

    @TableField("event_type")
    private Integer eventType;

    @TableField("signal_code_name")
    private String signalCodeName;

    @TableField("signal_code_type")
    private Integer signalCodeType;

    @TableField("signal_code_value")
    private String signalCodeValue;

    @TableField("root_arm_content")
    private String rootArmContent;

    @TableField("root_arm_dbm")
    private String rootArmDbm;

    @TableField("root_arm_source")
    private String rootArmSource;

    @TableField("root_arm_equ_name")
    private String rootArmEquName;

    @TableField("root_arm_equ_code")
    private String rootArmEquCode;

    @TableField("root_arm_equ_type")
    private String rootArmEquType;

    @TableField("root_arm_level")
    private Float rootArmLevel;

    @TableField("root_arm_equ_type_code")
    private Float rootArmEquTypeCode;

    @TableField("root_arm_code")
    private Float rootArmCode;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getSignalCodeName() {
        return signalCodeName;
    }

    public void setSignalCodeName(String signalCodeName) {
        this.signalCodeName = signalCodeName;
    }

    public Integer getSignalCodeType() {
        return signalCodeType;
    }

    public void setSignalCodeType(Integer signalCodeType) {
        this.signalCodeType = signalCodeType;
    }

    public String getSignalCodeValue() {
        return signalCodeValue;
    }

    public void setSignalCodeValue(String signalCodeValue) {
        this.signalCodeValue = signalCodeValue;
    }

    public String getRootArmContent() {
        return rootArmContent;
    }

    public void setRootArmContent(String rootArmContent) {
        this.rootArmContent = rootArmContent;
    }

    public String getRootArmDbm() {
        return rootArmDbm;
    }

    public void setRootArmDbm(String rootArmDbm) {
        this.rootArmDbm = rootArmDbm;
    }

    public String getRootArmSource() {
        return rootArmSource;
    }

    public void setRootArmSource(String rootArmSource) {
        this.rootArmSource = rootArmSource;
    }

    public String getRootArmEquName() {
        return rootArmEquName;
    }

    public void setRootArmEquName(String rootArmEquName) {
        this.rootArmEquName = rootArmEquName;
    }

    public String getRootArmEquCode() {
        return rootArmEquCode;
    }

    public void setRootArmEquCode(String rootArmEquCode) {
        this.rootArmEquCode = rootArmEquCode;
    }

    public String getRootArmEquType() {
        return rootArmEquType;
    }

    public void setRootArmEquType(String rootArmEquType) {
        this.rootArmEquType = rootArmEquType;
    }

    public Float getRootArmLevel() {
        return rootArmLevel;
    }

    public void setRootArmLevel(Float rootArmLevel) {
        this.rootArmLevel = rootArmLevel;
    }

    public Float getRootArmEquTypeCode() {
        return rootArmEquTypeCode;
    }

    public void setRootArmEquTypeCode(Float rootArmEquTypeCode) {
        this.rootArmEquTypeCode = rootArmEquTypeCode;
    }

    public Float getRootArmCode() {
        return rootArmCode;
    }

    public void setRootArmCode(Float rootArmCode) {
        this.rootArmCode = rootArmCode;
    }
}
