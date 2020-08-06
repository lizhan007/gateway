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
 * 车站表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_T_STATION")
public class SysTStation extends Model<SysTStation> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;

    @TableField("STATION_NAME")
    private String stationName;

    @TableField("STATION_CODE")
    private String stationCode;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("DISPLAY_NUMBER")
    private String displayNumber;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
