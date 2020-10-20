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
            " WHERE SR.DEV_ID = #{devId} ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U")
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
            "ORDER BY COLLECT_TYPE_ID ASC, INTERFACE_TYPE_ID ASC) AS U</script>")
    List<Map> listGroupCollections(@Param("devs") List<String> device);


    @Select("<script>SELECT * FROM (\n" +
            "SELECT MAX(DEV_ID) AS DEV_ID , MAX(MAJOR) AS MARJOR, KEY_ID, MAX(CODE_NAME) AS CODE_NAME, MAX(TYPE_NAME) AS TYPE_NAME, MAX(INTER_NAME) AS INTER_NAME ,MAX(SRC_NAME) AS SRC_NAME,MAX(DATA_TYPE) AS DATA_TYPE\n" +
            "FROM ( \n" +
            "SELECT E.DEV_ID AS DEV_ID, E.MAJOR AS MAJOR, E.`KEY_ID` AS KEY_ID, E.`CODE_NAME` AS CODE_NAME, E.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME , 4 AS DATA_TYPE FROM  \n" +
            "(SELECT EQ.MAJOR, EQ.`KEY_ID`, EQ.`CODE_NAME`, ET.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_ENUM_QUANTITY_DEF EQ \n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF ET ON EQ.`CODE_TYPE` = ET.`TYPE_ID` \n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON EQ.`KEY_ID` = SR.`KEY_ID`) E  \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON E.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID` \n" +
            "\n" +
            "UNION \n" +
            "\n" +
            "SELECT A.DEV_ID AS DEV_ID, A.MAJOR AS MAJOR, A.`KEY_ID` AS KEY_ID, A.`CODE_NAME` AS CODE_NAME, A.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME, 1 AS DATA_TYPE FROM  (SELECT AQ.MAJOR, AQ.`KEY_ID`, AQ.`CODE_NAME`, AT.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_ANALOG_QUANTITY_DEF AQ  \n" +
            "LEFT JOIN SYS_ANALOG_TYPE_DEF AT ON AQ.`CODE_TYPE` = AT.`TYPE_ID` \n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON AQ.`KEY_ID` = SR.`KEY_ID`) A  \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON A.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID` \n" +
            "UNION \n" +
            "SELECT \"\" AS DEV_ID, MAJOR, KEY_ID,CODE_NAME, \"\" AS TYPE_NAME, \"\" AS INTERFACE_TYPE_NAME, \"\" AS SRC_NAME, 8 AS DATA_TYPE FROM casco_zhgs.SYS_VIRTUAL_QUANTITY_DEF\n" +
            "\n" +
            "UNION \n" +
            "\n" +
            "SELECT D.DEV_ID AS DEV_ID, D.MAJOR AS MAJOR, D.`KEY_ID` AS KEY_ID, D.`CODE_NAME` AS CODE_NAME, D.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME, 0 AS DATA_TYPE FROM  \n" +
            "(SELECT DQ.MAJOR, DQ.`KEY_ID`, DQ.`CODE_NAME`, DT.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_DIGIT_QUANTITY_DEF DQ \n" +
            "LEFT JOIN SYS_DIGIT_TYPE_DEF DT ON DQ.`CODE_TYPE` = DT.`TYPE_ID`\n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON DQ.`KEY_ID` = SR.`KEY_ID`) D \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON D.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID`) H GROUP BY KEY_ID) N " +
            "WHERE KEY_ID LIKE CONCAT('%',#{keyId},'%') " +
            "AND DATA_TYPE = #{datatype}\n " +
            "AND CODE_NAME LIKE CONCAT('%',#{codeName},'%') " +
            "LIMIT #{start}, #{limit}</script>")
    List<Map> listCollections(@Param("keyId") String keyId, @Param("codeName") String codeName, @Param("datatype") int DataType, @Param("start")int start, @Param("limit")int limit);


    @Select("<script>SELECT COUNT(1) FROM (\n" +
            "SELECT MAX(DEV_ID) AS DEV_ID , MAX(MAJOR) AS MARJOR, KEY_ID, MAX(CODE_NAME) AS CODE_NAME, MAX(TYPE_NAME) AS TYPE_NAME, MAX(INTER_NAME) AS INTER_NAME ,MAX(SRC_NAME) AS SRC_NAME,MAX(DATA_TYPE) AS DATA_TYPE\n" +
            "FROM ( \n" +
            "SELECT E.DEV_ID AS DEV_ID, E.MAJOR AS MAJOR, E.`KEY_ID` AS KEY_ID, E.`CODE_NAME` AS CODE_NAME, E.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME , 4 AS DATA_TYPE FROM  \n" +
            "(SELECT EQ.MAJOR, EQ.`KEY_ID`, EQ.`CODE_NAME`, ET.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_ENUM_QUANTITY_DEF EQ \n" +
            "LEFT JOIN SYS_ENUM_TYPE_DEF ET ON EQ.`CODE_TYPE` = ET.`TYPE_ID` \n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON EQ.`KEY_ID` = SR.`KEY_ID`) E  \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON E.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID` \n" +
            "\n" +
            "UNION \n" +
            "\n" +
            "SELECT A.DEV_ID AS DEV_ID, A.MAJOR AS MAJOR, A.`KEY_ID` AS KEY_ID, A.`CODE_NAME` AS CODE_NAME, A.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME, 1 AS DATA_TYPE FROM  (SELECT AQ.MAJOR, AQ.`KEY_ID`, AQ.`CODE_NAME`, AT.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_ANALOG_QUANTITY_DEF AQ  \n" +
            "LEFT JOIN SYS_ANALOG_TYPE_DEF AT ON AQ.`CODE_TYPE` = AT.`TYPE_ID` \n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON AQ.`KEY_ID` = SR.`KEY_ID`) A  \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON A.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID` \n" +
            "UNION \n" +
            "SELECT \"\" AS DEV_ID, MAJOR, KEY_ID,CODE_NAME, \"\" AS TYPE_NAME, \"\" AS INTERFACE_TYPE_NAME, \"\" AS SRC_NAME, 8 AS DATA_TYPE FROM casco_zhgs.SYS_VIRTUAL_QUANTITY_DEF\n" +
            "\n" +
            "UNION \n" +
            "\n" +
            "SELECT D.DEV_ID AS DEV_ID, D.MAJOR AS MAJOR, D.`KEY_ID` AS KEY_ID, D.`CODE_NAME` AS CODE_NAME, D.`TYPE_NAME` AS TYPE_NAME, SI.`INTERFACE_TYPE_NAME` AS INTER_NAME, SI.`INTERFACE_SOURCE_NAME` AS SRC_NAME, 0 AS DATA_TYPE FROM  \n" +
            "(SELECT DQ.MAJOR, DQ.`KEY_ID`, DQ.`CODE_NAME`, DT.`TYPE_NAME`, SR.`INTERFACE_TYPE_ID`,SR.DATA_TYPE, SR.`DEV_ID` FROM SYS_DIGIT_QUANTITY_DEF DQ \n" +
            "LEFT JOIN SYS_DIGIT_TYPE_DEF DT ON DQ.`CODE_TYPE` = DT.`TYPE_ID`\n" +
            "LEFT JOIN SYS_RELATE_COLLECTION_DEF SR ON DQ.`KEY_ID` = SR.`KEY_ID`) D \n" +
            "LEFT JOIN SYS_INTERFACE_TYPE_DEF SI ON D.INTERFACE_TYPE_ID = SI.`INTERFACE_TYPE_ID`) H GROUP BY KEY_ID) N " +
            "WHERE KEY_ID LIKE CONCAT('%',#{keyId},'%')\n " +
            "AND DATA_TYPE = #{datatype}\n " +
            "AND CODE_NAME LIKE CONCAT('%',#{codeName},'%')</script>")
    int countCollections(@Param("keyId") String keyId, @Param("codeName") String codeName, @Param("datatype") int DataType);


}
