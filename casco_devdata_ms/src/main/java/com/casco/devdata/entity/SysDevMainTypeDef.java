package com.casco.devdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备主类型定义表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DEV_MAIN_TYPE_DEF")
public class SysDevMainTypeDef extends Model<SysDevMainTypeDef> {

    private static final long serialVersionUID = 1L;

    @TableId("DEV_MAIN_TYPE_ID")
    private String devMainTypeId;

    @TableField("DEV_MAIN_TYPE_NAME")
    private String devMainTypeName;

    @TableField("MAJOR")
    private String major;

    @TableField("DISPLAY_NUMBER")
    private Integer displayNumber;


    @Override
    protected Serializable pkVal() {
        return this.devMainTypeId;
    }

}
