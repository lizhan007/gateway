package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组件字典表
 * </p>
 *
 * @author yeexun
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_COMPONENTS_DICTIONARY")
public class SysComponentsDictionary extends Model<SysComponentsDictionary> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    private Long parentId;

    /**
     * 级别
     */
    private Integer level;

    private String name;

    /**
     * 0：数据接入 1：业务中台
     */
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.dictId;
    }

}
