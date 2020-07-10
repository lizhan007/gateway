package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
public class SysAlarmTable extends Model<SysAlarmTable> {

    private static final long serialVersionUID = 1L;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("ARM_UUID")
    private String armUuid;

    @TableField("ARM_EQU_TYPE")
    private String armEquType;

    @TableField("ARM_EQU_NAME")
    private String armEquName;

    @TableField("ARM_CONTENT")
    private String armContent;

    @TableField("ARM_EQU_CODE")
    private String armEquCode;

    @TableField("ARM_ADD_EQU")
    private String armAddEqu;

    @TableField("ARM_SOURCE")
    private String armSource;

    @TableField("ARM_DBM")
    private String armDbm;

    @TableField("ARM_HAPPEN_TIME")
    private LocalDateTime armHappenTime;

    @TableField("ARM_RESTORE_TIME")
    private LocalDateTime armRestoreTime;

    @TableField("ARM_FAULT_BEGIN")
    private Float armFaultBegin;

    @TableField("ARM_FAULT_BEG_MS")
    private Float armFaultBegMs;

    @TableField("ARM_FAULT_END")
    private Float armFaultEnd;

    @TableField("ARM_FAULT_END_MS")
    private Float armFaultEndMs;

    @TableField("ARM_CODE")
    private Float armCode;

    @TableField("ARM_EQU_TYPECODE")
    private Float armEquTypecode;

    @TableField("ARM_LOGIC_TYPE1")
    private Float armLogicType1;

    @TableField("ARM_LOGIC_TYPE2")
    private Float armLogicType2;

    @TableField("ARM_LOGIC_TYPE3")
    private Float armLogicType3;

    @TableField("ARM_LEVEL")
    private Float armLevel;

    @TableField("ARM_IS_MAINTAIN")
    private Float armIsMaintain;

    @TableField("ARM_SAVE_NO")
    private Float armSaveNo;

    @TableField("ARM_ADD_JSON")
    private String armAddJson;

    @TableField("MAJOR")
    private String major;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
