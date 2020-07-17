package com.casco.devdata.mapper;

import com.casco.devdata.controller.vo.SysDevMainTypeDefVo;
import com.casco.devdata.entity.SysDevMainTypeDef;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备主类型定义表 Mapper 接口
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
public interface SysDevMainTypeDefMapper extends BaseMapper<SysDevMainTypeDef> {

    @Select("SELECT * FROM SYS_DEV_MAIN_TYPE_DEF \n" +
            "WHERE DEV_MAIN_TYPE_ID IN (\n" +
            "\tSELECT DISTINCT DEV_MAIN_TYPE_ID \n" +
            "\tFROM SYS_DEV_TYPE_DEF \n" +
            "\tWHERE DEV_TYPE_ID IN (\n" +
            "\t\tSELECT DISTINCT DEV_TYPE_ID FROM SYS_DEV_LIST\n" +
            "\t)\n" +
            ") AND DISPLAY_NUMBER <> -1\n" +
            "ORDER BY DISPLAY_NUMBER ASC")
    public List<SysDevMainTypeDef> listValidMainType();
}
