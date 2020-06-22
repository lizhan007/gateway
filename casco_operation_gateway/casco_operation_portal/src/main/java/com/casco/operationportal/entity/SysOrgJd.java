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
 * 机构管辖表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ORG_JD")
public class SysOrgJd extends Model<SysOrgJd> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    private String uuid;

    @TableField("ORG_ID")
    private String orgId;

    @TableField("LINE_ID")
    private String lineId;

    @TableField("DEV_ATTR")
    private String devAttr;

    @TableField("STATION_ID")
    private String stationId;

    @TableField("DEV_ID")
    private String devId;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
