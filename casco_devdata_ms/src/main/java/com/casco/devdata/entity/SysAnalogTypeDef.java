package com.casco.devdata.entity;

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
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAnalogTypeDef extends Model<SysAnalogTypeDef> {

    private static final long serialVersionUID = 1L;

    @TableField("TYPE_ID")
    private Integer typeId;

    @TableField("TYPE_NAME")
    private String typeName;

    @TableField("ATTR_GROUP")
    private String attrGroup;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
