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
@TableName("SYS_ENUM_QUANTITY_DEF")
public class SysEnumQuantityDef extends Model<SysEnumQuantityDef> {

    private static final long serialVersionUID = 1L;

    @TableField("LINE_CODE")
    private String lineCode;

    @TableField("MAJOR")
    private String major;

    @TableField("SOURCE_NAME")
    private String sourceName;

    @TableField("KEY_ID")
    private String keyId;

    @TableField("INTERFACE_ID")
    private Integer interfaceId;

    @TableField("CODE_TYPE")
    private Integer codeType;

    @TableField("CODE_ID")
    private Integer codeId;

    @TableField("CODE_NAME")
    private String codeName;

    @TableField("ENABLED")
    private Integer enabled;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
