package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DEV_LIST")
public class SysDevList extends Model<SysDevList> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DEV_ID", type = IdType.ASSIGN_UUID)
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
