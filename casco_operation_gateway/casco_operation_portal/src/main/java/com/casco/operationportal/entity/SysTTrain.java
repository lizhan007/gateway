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
 * 列车表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_T_TRAIN")
public class SysTTrain extends Model<SysTTrain> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    private String uuid;

    @TableField("TRAIN_ID")
    private String trainId;

    @TableField("LINE_NAME")
    private String lineName;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
