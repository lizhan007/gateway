package com.casco.opgw.alarmhandler.config;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.alarmhandler.config.model.ISCSAlarmCfgModel;
import com.casco.opgw.alarmhandler.config.model.TrainAlarmCfgModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(1)
public class InitISCSAlarmRule implements CommandLineRunner {

    //iscs缓存数据
    public static List<ISCSAlarmCfgModel> iscsAlarmCfgModelList
            = new ArrayList<>();

    public static ConcurrentHashMap<String, Object> iscsCache
            = new ConcurrentHashMap<>();

    private static final int COL_NUM_LINE           = 0;
    private static final int COL_NUM_STATION        = 1;
    private static final int COL_NUM_EQUIPTYPE      = 3;
    private static final int COL_NUM_EQUIPLOC       = 4;
    private static final int COL_NUM_EQUIPCODE      = 5;
    private static final int COL_NUM_ALARMCONTENT   = 6;
    private static final int COL_NUM_VARTYPE        = 7;
    private static final int COL_NUM_VARNAME        = 15;//13
    private static final int COL_NUM_ALARMLEVEL     = 9;


    @Override
    public void run(String... args) throws Exception {

        XSSFWorkbook wb = null;
        /**
         * /data/serverconfig/水泵系统配置表.xlsx
         * d:\\水泵系统配置表.xlsx
         */
        File cfgFile =
                ResourceUtils.getFile("/home/data/serverconfig/水泵系统配置表.xlsx");
        //ResourceUtils.getFile("D:/07.yeeyun/04.project/10.CASCO/10.casco_git/20200920/gateway/config/水泵系统配置表.xlsx");
        InputStream in = new FileInputStream(cfgFile);
        wb = new XSSFWorkbook(in);

        XSSFSheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {

            if(row.getRowNum() == 0){
                continue;
            }

            if(row.getCell(1) == null ||
                    row.getCell(1).toString().length() == 0){
                break;
            }
            if(!row.getCell(COL_NUM_VARTYPE).toString().equals("DI")){
                //全部是数字信号
                continue;
            }

            if(row.getCell(COL_NUM_ALARMLEVEL) == null
            || row.getCell(COL_NUM_ALARMLEVEL).toString().length() == 0){
                //告警等级不允许为空
                continue;
            }



            ISCSAlarmCfgModel iscsAlarmCfgModel
                    = new ISCSAlarmCfgModel();

            iscsAlarmCfgModel.setLine(row.getCell(COL_NUM_LINE).toString());
            iscsAlarmCfgModel.setStation(row.getCell(COL_NUM_STATION).toString());
            iscsAlarmCfgModel.setEquipType(row.getCell(COL_NUM_EQUIPTYPE).toString());
            iscsAlarmCfgModel.setEquipLocation(row.getCell(COL_NUM_EQUIPLOC).toString());
            iscsAlarmCfgModel.setAlarmContent(row.getCell(COL_NUM_ALARMCONTENT).toString());
            iscsAlarmCfgModel.setVarName(row.getCell(COL_NUM_VARNAME).toString());
            iscsAlarmCfgModel.setEquipCode(row.getCell(COL_NUM_EQUIPCODE).toString());
            iscsAlarmCfgModel.setAlarmLevel(row.getCell(COL_NUM_ALARMLEVEL).toString());
            iscsAlarmCfgModelList.add(iscsAlarmCfgModel);
        }
    }
}
