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


    @Select("SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, KEY_ID,\n" +
            "      CASE U.DATA_TYPE \n" +
            "            WHEN '0' THEN U.DIGIT_TYPE_NAME \n" +
            "            WHEN '2' THEN U.ENUM_TYPE_NAME \n" +
            "            WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            "            END AS RES_TYPE_NAME\n" +
            "            FROM (SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            "            SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            "            SI.INTERFACE_SOURCE_NAME AS SRC_NAME \n" +
            "            FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            "            LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            "            LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            "            LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            "            LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            "            WHERE SR.DEV_ID = #{devId} ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U")
    List<Map> listDevAttr(@Param("devId") String devId);


    @Select("SELECT COLLECT_TYPE_ID, SEM.`TYPE_NAME`, SEM.`ENUM_MEAN`, SEM.`ENUM_VALUE` FROM(\n" +
            "SELECT COLLECT_TYPE_ID, TYPE_NAME FROM (SELECT COLLECT_TYPE_ID FROM SYS_RELATE_COLLECTION_DEF WHERE DATA_TYPE = 4) AS SR \n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF ED ON SR.`COLLECT_TYPE_ID` = ED.TYPE_ID) AS CT \n" +
            "RIGHT JOIN SYS_ENUM_MEAN_DEF SEM ON CT.TYPE_NAME = SEM.`TYPE_NAME`")
    List<Map> listEnumAttr();

}
