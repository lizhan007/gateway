package com.casco.devdata.mapper;

import com.casco.devdata.entity.SysDevTypeDef;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备类型定义表 Mapper 接口
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
public interface SysDevTypeDefMapper extends BaseMapper<SysDevTypeDef> {

    @Select("SELECT * FROM SYS_DEV_TYPE_DEF WHERE DEV_TYPE_ID IN (SELECT DISTINCT DEV_TYPE_ID FROM SYS_DEV_LIST)")
    public List<SysDevTypeDef> listValidType();
}
