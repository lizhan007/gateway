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
 * 机构管辖表
 * </p>
 *
 * @author yeexun
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ORG_JD")
public class SysOrgJd extends Model<SysOrgJd> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
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
