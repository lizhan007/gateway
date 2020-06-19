package com.casco.devdata.entity;

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
 * 人员表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;

    @TableField("ATTR")
    private String attr;

    @TableField("ACCOUNT")
    private String account;

    @TableField("PASSWORD")
    private String password;

    @TableField("ORG_ID")
    private String orgId;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
