package com.casco.devdata.entity;

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
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ANALOG_QUANTITY_DEF")
public class SysAnalogQuantityDef extends Model<SysAnalogQuantityDef> {

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

    @TableField("TIMES")
    private Float times;

    @TableField("UNIT")
    private String unit;

    @TableField("QUENCY")
    private Integer quency;

    @TableField("ENABLED")
    private Integer enabled;

    @TableField("ALARM_UP_LIM")
    private Float alarmUpLim;

    @TableField("ALARM_LO_LIM")
    private Float alarmLoLim;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
