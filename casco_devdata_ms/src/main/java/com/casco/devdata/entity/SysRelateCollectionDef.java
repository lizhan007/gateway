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
@TableName("SYS_RELATE_COLLECTION_DEF")
public class SysRelateCollectionDef extends Model<SysRelateCollectionDef> {

    private static final long serialVersionUID = 1L;

    @TableField("LINE_CODE")
    private String lineCode;

    @TableField("STATION_CODE")
    private String stationCode;

    @TableField("DEV_ID")
    private String devId;

    @TableField("DATA_TYPE")
    private Integer dataType;

    @TableField("COLLECT_TYPE_ID")
    private Integer collectTypeId;

    @TableField("INTERFACE_TYPE_ID")
    private Integer interfaceTypeId;

    @TableField("KEY_ID")
    private String keyId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
