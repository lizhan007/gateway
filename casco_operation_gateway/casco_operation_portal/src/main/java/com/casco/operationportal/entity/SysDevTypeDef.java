package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备类型定义表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DEV_TYPE_DEF")
public class SysDevTypeDef extends Model<SysDevTypeDef> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DEV_TYPE_ID", type = IdType.ASSIGN_UUID)
    private String devTypeId;

    @TableField("DEV_TYPE_NAME")
    private String devTypeName;

    @TableField("DEV_MAIN_TYPE_ID")
    private String devMainTypeId;

    @TableField("MAJOR")
    private String major;

    @Override
    protected Serializable pkVal() {
        return this.devTypeId;
    }

}
