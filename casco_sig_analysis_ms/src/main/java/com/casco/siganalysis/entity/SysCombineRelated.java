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
public class SysCombineRelated extends Model<SysCombineRelated> {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String armSceneId;

    private Float armCode;

    private String armCodeName;

    private String armImagePath;

    private Boolean isDefaultImage;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
