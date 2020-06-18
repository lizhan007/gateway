package com.casco.opgw.alarmhandler.config;

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
    private static final int COL_NUM_VARNAME        = 13;


    @Override
    public void run(String... args) throws Exception {

        XSSFWorkbook wb = null;
        File cfgFile =
                ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/成都9号线水泵系统报警定义表.xlsx");

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

            ISCSAlarmCfgModel iscsAlarmCfgModel
                    = new ISCSAlarmCfgModel();

            iscsAlarmCfgModel.setLine(row.getCell(COL_NUM_LINE).toString());
            iscsAlarmCfgModel.setStation(row.getCell(COL_NUM_STATION).toString());
            iscsAlarmCfgModel.setEquipType(row.getCell(COL_NUM_EQUIPTYPE).toString());
            iscsAlarmCfgModel.setEquipLocation(row.getCell(COL_NUM_EQUIPLOC).toString());
            iscsAlarmCfgModel.setAlarmContent(row.getCell(COL_NUM_ALARMCONTENT).toString());
            iscsAlarmCfgModel.setVarName(row.getCell(COL_NUM_VARNAME).toString());
            iscsAlarmCfgModel.setEquipCode(row.getCell(COL_NUM_EQUIPCODE).toString());

            iscsAlarmCfgModelList.add(iscsAlarmCfgModel);
        }

    }
}
