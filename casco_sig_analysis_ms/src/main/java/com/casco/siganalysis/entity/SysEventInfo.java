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
public class SysEventInfo extends Model<SysEventInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer eventType;

    private String signalCodeName;

    private Integer signalCodeType;

    private String signalCodeValue;

    private String rootArmContent;

    private String rootArmDbm;

    private String rootArmSource;

    private String rootArmEquName;

    private String rootArmEquCode;

    private String rootArmEquType;

    private Float rootArmLevel;

    private Float rootArmEquTypeCode;

    private Float rootArmCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
