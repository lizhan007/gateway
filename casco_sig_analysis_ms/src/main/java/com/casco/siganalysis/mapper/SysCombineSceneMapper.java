package com.casco.siganalysis.mapper;

import com.casco.siganalysis.entity.SysCombineScene;
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
 * @since 2020-07-09
 */
public interface SysCombineSceneMapper extends BaseMapper<SysCombineScene> {

    @Select("SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, KEY_ID,U.DEV_NAME,\n" +
            "                  CASE U.DATA_TYPE \n" +
            "                        WHEN '0' THEN U.DIGIT_TYPE_NAME\n" +
            "                        WHEN '4' THEN U.ENUM_TYPE_NAME\n" +
            "                        WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            "                        END AS RES_TYPE_NAME\n" +
            "                        FROM (SELECT SR.DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID,DL.DEV_NAME,\n" +
            "                        SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            "                        SI.INTERFACE_SOURCE_NAME AS SRC_NAME \n" +
            "                        FROM SYS_RELATE_COLLECTION_DEF SR\n" +
            "                        LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            "                        LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            "                        LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            "                        LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            "                        LEFT JOIN SYS_DEV_LIST AS DL ON DL.DEV_ID = SR.DEV_ID\n" +
            "                        WHERE SR.KEY_ID IN (SELECT DISTINCT SUBSTRING_INDEX(SUBSTRING_INDEX(a.arm_summary_set, ',', b.help_topic_id + 1) , ',', - 1)\n" +
            "            FROM sys_combine_scene a\n" +
            "            INNER JOIN mysql.help_topic b\n" +
            "                ON b.help_topic_id < (LENGTH(a.arm_summary_set) - LENGTH(REPLACE(a.arm_summary_set, ',', '')) + 1))) AS U")
    List<Map> listSceneSummurySet();
}
