package com.casco.siganalysis.entity;

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
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysCombineScene extends Model<SysCombineScene> {

    private static final long serialVersionUID = 1L;

    private String armSceneId;

    private String armSceneName;

    private String lineName;

    private String trainName;

    private String armSummarySet;

    private String armCodeSet;


    @Override
    protected Serializable pkVal() {
        return this.armSceneId;
    }

}
