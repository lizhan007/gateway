package com.casco.siganalysis.entity;

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
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_INTERFACE_TYPE_DEF")
public class SysInterfaceTypeDef extends Model<SysInterfaceTypeDef> {

    private static final long serialVersionUID = 1L;

    @TableField("INTERFACE_TYPE_ID")
    private Integer interfaceTypeId;

    @TableField("INTERFACE_TYPE_NAME")
    private String interfaceTypeName;

    @TableField("INTERFACE_SOURCE_NAME")
    private String interfaceSourceName;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
