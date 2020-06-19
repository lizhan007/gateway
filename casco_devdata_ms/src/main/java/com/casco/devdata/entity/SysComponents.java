package com.casco.devdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组件表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
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


    @Override
    protected Serializable pkVal() {
        return this.compId;
    }

}
