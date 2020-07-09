package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class SysEventAlarmInfo extends Model<SysEventAlarmInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String eventTypeId;

    private String armVehCode;

    private String armCodeValue;

    private String armContent;

    private String armDbm;

    private String armSource;

    private String armEquName;

    private String armEquCode;

    private String armEquType;

    private Float armLevel;

    private Float armEquTypeCode;

    private Float armCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
