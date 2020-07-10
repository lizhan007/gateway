package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ALARM_STATISTIC")
public class AlarmStatistic extends Model<AlarmStatistic> {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private String id;

    @TableField("MAJOR")
    private String major;

    @TableField("OCCUR_TIME")
    private LocalDateTime occurTime;

    @TableField("LEVEL")
    private String level;

    @TableField("TYPE")
    private String type;

    @TableField("CONTENT")
    private String content;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
