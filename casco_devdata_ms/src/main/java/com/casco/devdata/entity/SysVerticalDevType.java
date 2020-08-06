package com.casco.devdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @date: 2020/7/15 2:39 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_VERTICALSHOW_DEV_MAIN_TYPE")
public class SysVerticalDevType extends Model<SysVerticalDevType> {
//    private static final long serialVersionUID = 1L;

    @TableId("DEV_TYPE_ID")
    private String devTypeId;

    @TableField("DEV_TYPE_NAME")
    private String devTypeName;
}
