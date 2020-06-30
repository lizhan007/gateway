package com.casco.devdata.mapper;

import com.casco.devdata.entity.SysRelateCollectionDef;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yeexun
 * @since 2020-06-19
 */
public interface SysRelateCollectionDefMapper extends BaseMapper<SysRelateCollectionDef> {


    @Select("SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME,\n" +
            "CASE U.DATA_TYPE\n" +
            "\tWHEN '0' THEN U.DIGIT_TYPE_NAME\n" +
            "\tWHEN '2' THEN U.ENUM_TYPE_NAME\n" +
            "\tWHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            "END AS RES_TYPE_NAME\n" +
            "FROM \n" +
            "(SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, \n" +
            "SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            "SI.INTERFACE_SOURCE_NAME AS SRC_NAME \n" +
            "FROM SYS_RELATE_COLLECTION_DEF SR\n" +
            "LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            "LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID" +
            "  WHERE SR.DEV_ID = #{devId}) AS U")
    List<Map> listDevAttr(@Param("devId") String devId);
}
