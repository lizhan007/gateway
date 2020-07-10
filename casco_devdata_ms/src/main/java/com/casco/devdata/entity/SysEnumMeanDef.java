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
 * 
 * </p>
 *
 * @author yeexun
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ENUM_MEAN_DEF")
public class SysEnumMeanDef extends Model<SysEnumMeanDef> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Integer id;

    @TableField("TYPE_ID")
    private Integer typeId;

    @TableField("TYPE_NAME")
    private String typeName;

    @TableField("ENUM_VALUE")
    private String enumValue;

    @TableField("ENUM_DEF")
    private String enumDef;

    @TableField("ENUM_MEAN")
    private String enumMean;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
