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
import java.time.LocalDateTime;

/**
 * <p>
 * 组件表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_COMPONENTS")
public class SysComponents extends Model<SysComponents> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comp_id", type = IdType.AUTO)
    private Long compId;

    /**
     * 组件字典表id
     */
    private Long dictId;

    private String compName;

    private String compRemark;

    private String compJarPath;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    //来自SysComponentsDictionary 的属性
    /**
     * dictName
     */
    @TableField(exist = false)
    private String name;

    /**
     * 0：数据接入 1：业务中台
     */
    @TableField(exist = false)
    private Integer type;

    @Override
    protected Serializable pkVal() {
        return this.compId;
    }

}
