package com.casco.devdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SIG_LD_RECORD")
public class SigLdRecord extends Model<SigLdRecord> {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer eventType;

    private LocalDateTime happenTime;

    private String lineName;

    private String stationName;

    private String codeName;

    private Integer codeValue;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
