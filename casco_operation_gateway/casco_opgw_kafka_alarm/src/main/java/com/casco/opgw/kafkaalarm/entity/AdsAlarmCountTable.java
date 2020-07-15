package com.casco.opgw.kafkaalarm.entity;

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
 *
 * </p>
 *
 * @author yeexun
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ads_alarm_count")
public class AdsAlarmCountTable extends Model<AdsAlarmCountTable> {

    private static final long serialVersionUID = 1L;

    @TableField("alarm_count")
    private Integer alarmCount;

    @TableField("early_alarm_count")
    private Integer earlyAlarmCount;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
