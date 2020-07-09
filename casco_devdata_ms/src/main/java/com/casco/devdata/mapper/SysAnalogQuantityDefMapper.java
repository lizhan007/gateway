package com.casco.devdata.mapper;

import com.casco.devdata.entity.SysAnalogQuantityDef;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *   Mapper 接口
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
public interface SysAnalogQuantityDefMapper extends BaseMapper<SysAnalogQuantityDef> {

    @Select("<script>SELECT * FROM SYS_ANALOG_QUANTITY_DEF WHERE KEY_ID IN (\n" +
            "SELECT KEY_ID FROM SYS_RELATE_COLLECTION_DEF WHERE DATA_TYPE = 1 AND DEV_ID IN" +
            "<foreach collection=\"devs\" index = \"index\" item = \"devid\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{devid} \n" +
            "</foreach> \n" +
            ")</script>")
    List<SysAnalogQuantityDef> listAnalogUnit(@Param("devs") List<String> device);
}
