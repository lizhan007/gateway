package com.casco.operationportal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采集记录统计表
 * </p>
 *
 * @author yeexun
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_STATISTICS")
public class SysStatistics extends Model<SysStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据类型：0：数字，1：模拟，4：枚举
     */
    private Integer type;

    /**
     * 月份
     */
    private String month;

    /**
     * 是否有值 0：没有，1：有
     */
    @TableField("Is_there_a_value")
    private Integer isThereAValue;

    /**
     * 数量
     */
    private Long count;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
