package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("BAS_ANALOG_QUANTITY_RECORD")
public class BasAnalogQuantityRecord extends Model<BasAnalogQuantityRecord> {

    private static final long serialVersionUID = 1L;

    @TableField("LINE_CODE")
    private String lineCode;

    @TableField("STATION_CODE")
    private String stationCode;

    @TableField("DEV_TYPE")
    private String devType;

    @TableField("KEY_ID")
    private String keyId;

    @TableField("RECORD_TIME")
    private LocalDateTime recordTime;

    @TableField("VALUE")
    private Float value;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
