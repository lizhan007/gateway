package com.casco.siganalysis.entity;

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
 * 设备表
 * </p>
 *
 * @author yeexun
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DEV_LIST")
public class SysDevList extends Model<SysDevList> {

    private static final long serialVersionUID = 1L;

    @TableId("DEV_ID")
    private String devId;

    @TableField("DEV_NAME")
    private String devName;

    @TableField("DEV_TYPE_ID")
    private String devTypeId;

    @TableField("LINE_CODE")
    private String lineCode;

    @TableField("STATION_CODE")
    private String stationCode;


    @Override
    protected Serializable pkVal() {
        return this.devId;
    }

}
