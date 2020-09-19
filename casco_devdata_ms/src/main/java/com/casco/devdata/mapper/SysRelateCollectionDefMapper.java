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


    @Select("SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, I_TYPE_NAME, KEY_ID,INTERFACE_TYPE_ID,\n" +
            "      CASE U.DATA_TYPE \n" +
            "            WHEN '0' THEN U.DIGIT_TYPE_NAME \n" +
            "            WHEN '4' THEN U.ENUM_TYPE_NAME \n" +
            "            WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            "            END AS RES_TYPE_NAME\n" +
            "            FROM (SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            "            SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            "            SI.INTERFACE_SOURCE_NAME AS SRC_NAME, \n" +
            "            SI.INTERFACE_TYPE_NAME AS I_TYPE_NAME \n" +
            "            FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            "            LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            "            LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            "            LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            "            LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            "            WHERE SR.DEV_ID = #{devId} ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U")
    List<Map> listDevAttr(@Param("devId") String devId);



    @Select("<script>SELECT COLLECT_TYPE_ID, SEM.`TYPE_NAME`, SEM.`ENUM_MEAN`, SEM.`ENUM_VALUE` FROM(\n" +
            "SELECT COLLECT_TYPE_ID, TYPE_NAME FROM (SELECT DISTINCT COLLECT_TYPE_ID FROM SYS_RELATE_COLLECTION_DEF WHERE DATA_TYPE = 4 AND  \n" +
            "DEV_ID IN \n" +
            "<foreach collection=\"devs\" index = \"index\" item = \"devid\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{devid} \n" +
            "</foreach> \n" +
            ") AS SR \n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF ED ON SR.`COLLECT_TYPE_ID` = ED.TYPE_ID) AS CT \n" +
            "RIGHT JOIN SYS_ENUM_MEAN_DEF SEM ON CT.TYPE_NAME = SEM.`TYPE_NAME`</script>")
    List<Map> listOldEnumAttr(@Param("devs") List<String> device);



    @Select("<script>SELECT * FROM (\n" +
            "SELECT SR.COLLECT_TYPE_ID, ED.TYPE_NAME FROM (SELECT DISTINCT COLLECT_TYPE_ID FROM SYS_RELATE_COLLECTION_DEF WHERE DATA_TYPE = 4 AND DEV_ID IN" +
            "<foreach collection=\"devs\" index = \"index\" item = \"devid\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{devid} \n" +
            "</foreach> \n" +
            ") AS SR \n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF ED ON SR.`COLLECT_TYPE_ID` = ED.TYPE_ID ) AS CT\n" +
            "RIGHT JOIN (SELECT * FROM SYS_ENUM_MEAN_DEF WHERE TYPE_ID IN (SELECT DISTINCT COLLECT_TYPE_ID FROM SYS_RELATE_COLLECTION_DEF WHERE DATA_TYPE = 4 \n" +
            "AND DEV_ID IN" +
            "<foreach collection=\"devs\" index = \"index\" item = \"devid\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{devid} \n" +
            "</foreach> \n" +
            ")) SEM ON CT.TYPE_NAME = SEM.`TYPE_NAME`</script>")
    List<Map> listEnumAttr(@Param("devs") List<String> device);

    @Select("<script>SELECT DEV_ID, COUNT(KEY_ID) AS NUM FROM sys_relate_collection_def WHERE KEY_ID IN \n" +
            "<foreach collection=\"keys\" index = \"index\" item = \"key\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{key} \n" +
            "</foreach> \n" +
            " GROUP BY dev_id ORDER BY NUM LIMIT 1</script>")
    List<Map> getLongestDevByKeys(@Param("keys") List<String> keys);


    @Select("SELECT collect_name FROM sys_dev_type_relate_collection_def " +
            "WHERE dev_type_id = (SELECT DEV_TYPE_ID FROM sys_dev_list WHERE DEV_ID = #{devId}) " +
            "ORDER BY  COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC")
    List<String> getCollectionsByDevType(@Param("devId") String devId);

    @Select("<script>SELECT * FROM sys_relate_collection_def AS SR LEFT JOIN sys_dev_type_relate_collection_def " +
            "AS SD ON SR.`COLLECT_TYPE_ID` = SD.`collect_type_id` WHERE DEV_ID IN " +
            "<foreach collection=\"devs\" index = \"index\" item = \"dev\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{dev} \n" +
            "</foreach> \n" +
            "</script>")
    List<Map> listBasCollection(@Param("devs") List<String> device);



    @Select("SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, I_TYPE_NAME, KEY_ID,INTERFACE_TYPE_ID,\n" +
            "CASE U.DATA_TYPE \n" +
            " WHEN '0' THEN U.DIGIT_TYPE_NAME\n" +
            " WHEN '4' THEN U.ENUM_TYPE_NAME \n" +
            " WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            " END AS RES_TYPE_NAME,\n" +
            " CASE U.DATA_TYPE \n" +
            " WHEN '0' THEN U.DIGIT_ATTR_GROUP\n" +
            " WHEN '4' THEN U.ENUM_ATTR_GROUP \n" +
            " WHEN '1' THEN U.ANALOG_ATTR_GROUP\n" +
            " END AS ATTR_GROUP\n" +
            " FROM (SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            " SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            " SD.ATTR_GROUP AS DIGIT_ATTR_GROUP, SE.ATTR_GROUP AS ENUM_ATTR_GROUP, SA.ATTR_GROUP AS ANALOG_ATTR_GROUP,\n" +
            " SI.INTERFACE_SOURCE_NAME AS SRC_NAME, \n" +
            " SI.INTERFACE_TYPE_NAME AS I_TYPE_NAME \n" +
            " FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            " LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            " LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            " LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            " LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            " WHERE SR.DEV_ID = #{devId} ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U ORDER BY ATTR_GROUP")
    List<Map> listGroupDevAttr(@Param("devId") String devId);



    @Select("<script>SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, I_TYPE_NAME, KEY_ID, INTERFACE_TYPE_ID,\n" +
            "CASE U.DATA_TYPE \n" +
            " WHEN '0' THEN U.DIGIT_TYPE_NAME\n" +
            " WHEN '4' THEN U.ENUM_TYPE_NAME \n" +
            " WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            " END AS RES_TYPE_NAME,\n" +
            " CASE U.DATA_TYPE \n" +
            " WHEN '0' THEN U.DIGIT_ATTR_GROUP\n" +
            " WHEN '4' THEN U.ENUM_ATTR_GROUP \n" +
            " WHEN '1' THEN U.ANALOG_ATTR_GROUP\n" +
            " END AS ATTR_GROUP\n" +
            " FROM (SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            " SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            " SD.ATTR_GROUP AS DIGIT_ATTR_GROUP, SE.ATTR_GROUP AS ENUM_ATTR_GROUP, SA.ATTR_GROUP AS ANALOG_ATTR_GROUP,\n" +
            " SI.INTERFACE_SOURCE_NAME AS SRC_NAME, \n" +
            " SI.INTERFACE_TYPE_NAME AS I_TYPE_NAME \n" +
            " FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            " LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            " LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            " LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            " LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            " WHERE SR.DEV_ID IN " +
            "<foreach collection=\"devs\" index = \"index\" item = \"dev\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{dev} \n" +
            "</foreach> \n" +
            "ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U ORDER BY ATTR_GROUP</script>")
    List<Map> listGroupCollections(@Param("devs") List<String> device);


    @Select("<script>SELECT DEV_ID, DATA_TYPE, COLLECT_TYPE_ID,SRC_NAME, I_TYPE_NAME, KEY_ID,INTERFACE_TYPE_ID,DEV_NAME, DEV_TYPE_ID,\n" +
            "CASE U.DATA_TYPE \n" +
            " WHEN '0' THEN U.DIGIT_TYPE_NAME\n" +
            " WHEN '4' THEN U.ENUM_TYPE_NAME \n" +
            " WHEN '1' THEN U.ANALOG_TYPE_NAME\n" +
            " END AS RES_TYPE_NAME\n" +
            " FROM (SELECT SR.DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            " SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            " SI.INTERFACE_SOURCE_NAME AS SRC_NAME, \n" +
            " SI.INTERFACE_TYPE_NAME AS I_TYPE_NAME,DL.DEV_NAME, DL.DEV_TYPE_ID\n" +
            " FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            " LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            " LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            " LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            " LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            " LEFT JOIN SYS_DEV_LIST AS DL ON SR.DEV_ID = DL.DEV_ID\n" +
            " WHERE \n" +
            " SR.DEV_ID LIKE CONCAT('%',#{devId},'%')\n" +
            " AND DL.DEV_TYPE_ID IN " +
            "<foreach collection=\"types\" index = \"index\" item = \"type\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{type} \n" +
            "</foreach> \n" +
            " AND DL.DEV_NAME LIKE CONCAT('%',#{devName},'%')\n" +
            " ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U LIMIT #{start}, #{limit}</script>")
    List<Map> listCollections(@Param("devId") String devId, @Param("devName") String devName, @Param("types") List<String> devTypes, int start, int limit);




    @Select("<script>SELECT COUNT(1)\n" +
            " FROM (SELECT SR.DEV_ID, DATA_TYPE, COLLECT_TYPE_ID, SR.KEY_ID, SR.INTERFACE_TYPE_ID,\n" +
            " SD.TYPE_NAME AS DIGIT_TYPE_NAME, SE.TYPE_NAME AS ENUM_TYPE_NAME, SA.TYPE_NAME AS ANALOG_TYPE_NAME,\n" +
            " SI.INTERFACE_SOURCE_NAME AS SRC_NAME, \n" +
            " SI.INTERFACE_TYPE_NAME AS I_TYPE_NAME,DL.DEV_NAME, DL.DEV_TYPE_ID\n" +
            " FROM SYS_RELATE_COLLECTION_DEF SR \n" +
            " LEFT JOIN SYS_DIGIT_TYPE_DEF AS SD ON SR.COLLECT_TYPE_ID = SD.TYPE_ID\n" +
            " LEFT JOIN SYS_ENUM_TYPE_DEF AS SE ON SR.COLLECT_TYPE_ID = SE.TYPE_ID\n" +
            " LEFT JOIN SYS_ANALOG_TYPE_DEF AS SA ON SR.COLLECT_TYPE_ID = SA.TYPE_ID\n" +
            " LEFT JOIN SYS_INTERFACE_TYPE_DEF AS SI ON SR.INTERFACE_TYPE_ID = SI.INTERFACE_TYPE_ID\n" +
            " LEFT JOIN SYS_DEV_LIST AS DL ON SR.DEV_ID = DL.DEV_ID\n" +
            " WHERE \n" +
            " SR.DEV_ID LIKE CONCAT('%',#{devId},'%')\n" +
            " AND DL.DEV_TYPE_ID IN " +
            "<foreach collection=\"types\" index = \"index\" item = \"type\" open= \"(\" separator=\",\" close=\")\">  \n" +
            "#{type} \n" +
            "</foreach> \n" +
            " AND DL.DEV_NAME LIKE CONCAT('%',#{devName},'%')\n" +
            " ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U</script>")
    int countCollections(@Param("devId") String devId, @Param("devName") String devName, @Param("types") List<String> devTypes, int start, int limit);





}
