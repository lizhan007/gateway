package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: xiot_platform
 * @description:
 * @author: CC
 * @date: 2020/7/21 4:08 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_alarm_table")
public class SysAlarmList extends Model<SysAlarmList> {
    private static final long serialVersionUID = 1L;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("ARM_UUID")
    private String armUUID;

    @TableField("ARM_EQU_TYPE")
    private String armEquType;

    @TableField("ARM_EQU_NAME")
    private String armEquName;

    @TableField("ARM_EQU_CODE")
    private String armEquCODE;

    @TableField("ARM_CONTENT")
    private String armContent;

    @TableField("ARM_ADD_EQU")
    private String armAddEqu;

    @TableField("ARM_SOURCE")
    private String armSource;

    @TableField("ARM_DBM")
    private String armDbm;

    @TableField("ARM_HAPPEN_TIME")
    private String armHappenTime;

    @TableField("ARM_RESTORE_TIME")
    private String armRestoreTime;

    @TableField("ARM_LEVEL")
    private Integer armLevel;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
