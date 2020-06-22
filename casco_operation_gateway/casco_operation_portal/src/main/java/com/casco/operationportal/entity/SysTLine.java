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
 * 线路表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_T_LINE")
public class SysTLine extends Model<SysTLine> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    private String uuid;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("LINE_CODE")
    private String lineCode;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
