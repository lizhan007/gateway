package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  
 * </p>
 *
 * @author yeexun
 * @since 2020-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DIGIT_TYPE_DEF")
public class SysDigitTypeDef extends Model<SysDigitTypeDef> {

    private static final long serialVersionUID = 1L;

    @TableField("TYPE_ID")
    private Integer typeId;

    @TableField("TYPE_NAME")
    private String typeName;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
