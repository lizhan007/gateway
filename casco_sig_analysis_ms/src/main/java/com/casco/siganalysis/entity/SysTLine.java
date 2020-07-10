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
 * 线路表
 * </p>
 *
 * @author yeexun
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_T_LINE")
public class SysTLine extends Model<SysTLine> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("LINE_CODE")
    private String lineCode;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
