package com.casco.operationportal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.entity.SysComponents;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 组件表 Mapper 接口
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
public interface SysComponentsMapper extends BaseMapper<SysComponents> {

    @Select("<script>" +
            " select c.*," +
            " d.name,d.type" +
            " from SYS_COMPONENTS c " +
            " join SYS_COMPONENTS_DICTIONARY d on c.dict_id=d.dict_id" +
            " where d.type = #{type}" +

            " <if test='dictId != null'>" +
            " AND c.dict_id = #{dictId}" +
            " </if>" +
            " </script>")
    Page<SysComponents> getList(Page<SysComponents> page,
                                @Param("type") Integer type,
                                @Param("dictId") Long dictId
    );
}
