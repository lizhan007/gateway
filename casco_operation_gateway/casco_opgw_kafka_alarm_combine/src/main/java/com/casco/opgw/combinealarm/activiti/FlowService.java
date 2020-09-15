package com.casco.opgw.combinealarm.activiti;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.combinealarm.db.TableInfoConstant;
import com.casco.opgw.combinealarm.dto.AlarmData;
import com.casco.opgw.combinealarm.service.impl.CacheManagerImpl;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FlowService implements JavaDelegate {

    @Resource(name = "TRAIN")
    private InfluxDB trainInfluxDB;

    @Resource(name = "SIG")
    private  InfluxDB sigInfluxDB;

    private Expression flowName;
    private Expression keys;
    private Expression values;
    private Expression contents;
    private Expression equNames;
    private Expression equCodes;
    private Expression duration;

    private String keysString;
    private String valuesString;
    private String contentsString;
    private String equNamesString;
    private String equCodesString;

    private String codeName;
    private Integer codeValue;
    private String lineName;
    private Long timestamp;

    private boolean armFlag = false;

    @Override
    public void execute(DelegateExecution execution) {
        String flowNameStr = (String)flowName.getValue(execution);
        String durationStr = (String)duration.getValue(execution);

        keysString = (String)keys.getValue(execution);
        valuesString = (String)values.getValue(execution);
        contentsString = (String)contents.getValue(execution);
        equNamesString = (String)equNames.getValue(execution);
        equCodesString = (String)equCodes.getValue(execution);

        codeName = (String)execution.getVariable("code");
        codeValue = (Integer)execution.getVariable("value");
        lineName = (String)execution.getVariable("line");
        timestamp = (Long)execution.getVariable("timestamp");

        armFlag = false;

        String startTime = LocalDateTime.ofEpochSecond(timestamp - Long.parseLong(durationStr),
                0, ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        String endTime = LocalDateTime.ofEpochSecond(timestamp + Long.parseLong(durationStr),
                0, ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        switch (flowNameStr) {
            case "CC_SLIP_SLIDE":
                CCSlipSlideTask(execution);
                break;
            case "TRAIN_SLIDE":
                trainSlideTask(execution, startTime, endTime);
                break;
            case "SLIDE_SUPPRESSION":
            case "SLIDE_ACTIVE":
            case "WHEEL_DIAMETER_DIFF":
                slideVehTask(execution, startTime, endTime);
                break;
            default:
                break;
        }
    }

    // 分析：CC检测到打滑
    private void CCSlipSlideTask(DelegateExecution execution) {
        Integer value = Integer.parseInt(valuesString);

        if (keysString.equals(codeName) && value.equals(codeValue)) {
            AlarmData alarmData = new AlarmData();
            alarmData.setLineName(lineName);
            alarmData.setArmHappenTime(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
            alarmData.setArmUuid(UUID.randomUUID().toString());
            alarmData.setArmContent(contentsString);
            alarmData.setArmEquName(equNamesString);
            alarmData.setArmEquCode(equCodesString);

            execution.setVariable("alarmDataString", JSON.toJSONString(alarmData));
            execution.setVariable("isCCAlarm", "true");
        } else {
            execution.setVariable("isCCAlarm", "false");
        }
    }

    // 分析：车辆监测到打滑
    private void trainSlideTask(DelegateExecution execution, String startTime, String endTime) {
        Integer value = Integer.parseInt(valuesString);
        String[] keyParts = this.keysString.split("\\.");
        String cmdPrefix = String.format(
                "SELECT * FROM %s WHERE time > '%s' AND time < '%s' ", TableInfoConstant.SIG_TABLE_DIGIT, startTime, endTime
        );

        if (keyParts.length == 6) {
            String cmd = String.format(
                    "%s AND line='%s' AND region='%s' AND srcId='%s' AND type='%s' AND pointcode='%s' tz('Asia/Shanghai')",
                    cmdPrefix, keyParts[1], keyParts[2], keyParts[3], keyParts[4], keyParts[5]
            );
            Query query = new Query(cmd, TableInfoConstant.SIG_DB_NAME);
            QueryResult results = sigInfluxDB.query(query);

            List<Map<String, Object>> sigCodeInfo = packageData(results);
            if (sigCodeInfo != null) {
                for (Map<String, Object> sig : sigCodeInfo) {
                    Float alarmValue = Float.parseFloat(sig.get("value").toString());
                    if (value - alarmValue == 0) {
                        String alarmDataStr = (String)execution.getVariable("alarmDataString");
                        if (!alarmDataStr.isEmpty()) {
                            AlarmData alarmData = JSON.parseObject(alarmDataStr, AlarmData.class);
                            alarmData.setArmContent(contentsString);
                            alarmData.setArmEquName(equNamesString);
                            alarmData.setArmEquCode(equCodesString);
                            execution.setVariable("alarmDataString", JSON.toJSONString(alarmData));
                            armFlag = true;
                            break;
                        }
                    }
                }
            }
        }
        if (this.armFlag) {
            execution.setVariable("isTrainAlarm", "true");
        } else {
            execution.setVariable("isTrainAlarm", "false");
        }
    }

    // 分析：防滑抑制、防滑激活、轮径差故障
    private void slideVehTask(DelegateExecution execution, String startTime, String endTime) {
        String[] keys = this.keysString.split(",");
        String[] values = this.valuesString.split(",");
        String[] contents = this.contentsString.split(",");
        String[] equNames = this.equNamesString.split(",");
        String[] equCodes = this.equCodesString.split(",");

        String flowNameStr = (String)flowName.getValue(execution);
        String cmdPrefix = String.format(
                "SELECT * FROM %s WHERE time > '%s' AND time < '%s' ", TableInfoConstant.VEHICLE_TABLE_DIGIT, startTime, endTime
        );
        if (keys.length != 0) {
            StringBuilder cmdForVeh = new StringBuilder();
            cmdForVeh.append(String.format("%s AND (", cmdPrefix));
            for (int i = 0; i < keys.length; i++) {
                cmdForVeh.append("pointcode = '");
                cmdForVeh.append(keys[i]);
                if (i != keys.length - 1) {
                    cmdForVeh.append("' OR ");
                } else {
                    cmdForVeh.append("') ORDER BY time tz('Asia/Shanghai')");
                }
            }
            Query queryForVeh = new Query(cmdForVeh.toString(), TableInfoConstant.VEHICLE_DB_NAME);
            QueryResult resultsForVeh = trainInfluxDB.query(queryForVeh);
            List<Map<String, Object>> vehCodeInfo = packageData(resultsForVeh);

            if (vehCodeInfo != null) {
                labelA:
                for (Map<String, Object> veh : vehCodeInfo) {
                    String alarmCode = veh.get("pointcode").toString();
                    Object alarmValue = veh.get("value");

                    for (int i = 0; i < keys.length; i++) {
                        // 找到原因：找到最早变位的点
                        if (keys[i].equals(alarmCode)
                                && compare(Integer.parseInt(values[i]), alarmValue)) {
                            CacheManagerImpl cacheManager = new CacheManagerImpl();
                            if (cacheManager.isContains(codeName)) {
                                String alarmDataStr = (String)execution.getVariable("alarmDataString");
                                if (!alarmDataStr.isEmpty()) {
                                    AlarmData alarmData = JSON.parseObject(alarmDataStr, AlarmData.class);
                                    if (flowNameStr.equals("SLIDE_SUPPRESSION")) {
                                        alarmData.setArmContent(contents[0]);
                                    } else {
                                        alarmData.setArmContent(contents[i]);
                                    }
                                    alarmData.setArmEquName(equNames[i]);
                                    alarmData.setArmEquCode(equCodes[i]);
                                    execution.setVariable("alarmDataString", JSON.toJSONString(alarmData));
                                    armFlag = true;
                                    break labelA;
                                }
                            }
                        }
                    }
                }
            }
        }
        switch (flowNameStr) {
            case "SLIDE_SUPPRESSION":           // 防滑抑制
                if (armFlag) {
                    execution.setVariable("isSlipSupp", "true");
                } else {
                    execution.setVariable("isSlipSupp", "false");
                }
                break;
            case "SLIDE_ACTIVE":               // 防滑激活
                if (armFlag) {
                    execution.setVariable("isSlideActive", "true");
                } else {
                    execution.setVariable("isSlideActive", "false");
                }
                break;
            case "WHEEL_DIAMETER_DIFF":       // 轮径差故障
                if (armFlag) {
                    execution.setVariable("isWheelDiameterDiff", "true");
                } else {
                    execution.setVariable("isWheelDiameterDiff", "false");
                }
                break;
            default:
                break;
        }
    }

    // influxDB查询结果转换
    private List<Map<String, Object>> packageData(QueryResult results) {
        if (results.getResults() == null) {
            return null;
        }

        QueryResult.Result oneResult = results.getResults().get(0);
        List<QueryResult.Series> seriesList = oneResult.getSeries();
        if (seriesList == null) {
            return null;
        }
        // 封装查询结果
        List<Map<String, Object>> dataList = new LinkedList<>();
        for (QueryResult.Series series : seriesList) {
            List<List<Object>> values = series.getValues();       //字段字集合
            List<String> columns = series.getColumns();           //字段名

            for (List<Object> value : values) {
                Map<String, Object> dataMap = new HashMap<>(columns.size());
                for (int j = 0; j < columns.size(); ++j){
                    dataMap.put(columns.get(j),value.get(j));
                }
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

    // 值比较
    private boolean compare(Integer first, Object second) {
        if (second instanceof Integer) {
            Integer tmp = (Integer)second;
            return first.equals(tmp);
        } else if (second instanceof Float) {
            Float tmp = (Float)second;
            return (first - tmp) == 0;
        } else if (second instanceof Double) {
            Double tmp = (Double)second;
            return (first - tmp) == 0;
        }
        return false;
    }
}
