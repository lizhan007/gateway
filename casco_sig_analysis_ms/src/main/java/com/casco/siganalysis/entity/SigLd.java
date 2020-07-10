package com.casco.siganalysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("SIG_LD")
public class SigLd extends Model<SigLd> {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer eventType;

    private String happenTime;

    private String lineName;

    private String stationName;

    private String codeName;

    private Integer codeValue;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
