package com.casco.devdata.entity;

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
 * 数据接入表
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DATA_ACCESS")
public class SysDataAccess extends Model<SysDataAccess> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("LINE_NAME")
    private String lineName;

    @TableField("LINE_CODE")
    private String lineCode;

    @TableField("STATION_NAME")
    private String stationName;

    @TableField("STATION_CODE")
    private String stationCode;

    @TableField("TRAIN_ID")
    private String trainId;

    @TableField("MAJOR")
    private String major;

    @TableField("DEV_ID")
    private String devId;

    @TableField("DEV_NAME")
    private String devName;

    private Long compId;

    private String compName;

    /**
     * 专业线路配置文件，前端传
     */
    private String lineFilePath;

    /**
     * jar配置文件，前端传
     */
    private String confFilePath;

    /**
     * 组件jar文件，前端传
     */
    private String compJarPath;

    /**
     * 运行时专业线路配置文件，后端生成
     */
    private String runtimeLineFilePath;

    /**
     * 运行时jar配置文件，后端生成
     */
    private String runtimeConfFilePath;

    /**
     * 运行时组件jar文件，后端生成
     */
    private String runtimeCompJarPath;

    /**
     * 资源情况 0：停止 1启动
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
