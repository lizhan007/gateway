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
@TableName("SYS_ENUM_MEAN_DEF")
public class SysEnumMeanDef extends Model<SysEnumMeanDef> {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private Integer id;

    @TableField("TYPE_ID")
    private Integer typeId;

    @TableField("TYPE_NAME")
    private String typeName;

    @TableField("ENUM_SUM")
    private Integer enumSum;

    @TableField("ENUM_VALUE")
    private Integer enumValue;

    @TableField("ENUM_MEAN")
    private String enumMean;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
