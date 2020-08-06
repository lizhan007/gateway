package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description:
 * @author: CC
 * @date: 2020/7/29 10:38 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ALARM_DEF")
public class AlarmDef extends Model<AlarmDef> {
    private static final long serialVersionUID = 1L;

    @TableField("alarm_code")
    private Integer alarmCode;

    @TableField("alarm_content")
    private String alarmContent;

    @TableField("alarm_level")
    private String alarmLevel;

    @TableField("major")
    private String major;

    @TableField("dev_type_name")
    private String devTypeName;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
