package com.casco.devdata.entity;

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
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDevTypeRelateCollectionDef extends Model<SysDevTypeRelateCollectionDef> {

    private static final long serialVersionUID = 1L;

    private String major;

    private Integer devTypeId;

    private String dataType;

    private Integer collectTypeId;

    private Integer interfaceTypeId;

    private String collectName;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
