package com.casco.opgw.alarmhandler.config;


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
public class InitTrainAlarmRule implements CommandLineRunner {

    //列车缓存的数据结构
    public static int isOverHaul; //检修状态 检修状态下不触发告警
    public static List<TrainAlarmCfgModel> trainAlarmCfgModelList
            = new ArrayList<>();
    //缓存变量
    public static ConcurrentHashMap<String, Object> trainCache
            = new ConcurrentHashMap<>();

    public static int OverHaulMode = 0; //是否检修，当前暂时使用synchronized保证线程安全

    public synchronized static  int getIsOverHaul() {
        return isOverHaul;
    }

    public synchronized static void setIsOverHaul(int isOverHaul) {
        InitTrainAlarmRule.isOverHaul = isOverHaul;
    }

    private static final int COL_NUM_LINE           = 6;
    private static final int COL_NUM_TRAIN          = 8;//7
    private static final int COL_NUM_VARNAME        = 1;
    private static final int COL_NUM_EQUIPTYPE      = 9;//8
    private static final int COL_NUM_ALARMCONTENT   = 2;
    private static final int COL_NUM_ALARMLEVEL     = 5;
    private static final int COL_NUM_ALARMPRO       = 4;
    private static final int COL_NUM_DATATYPE       = 3;

    @Override
    public void run(String... args) throws Exception {

        //1. 解析配置excel文件
        //本地测试：d:\\车辆映射表.xls
        //Linux:ResourceUtils.CLASSPATH_URL_PREFIX + "static/车辆映射表.xlsx"
        XSSFWorkbook wb = null;
        File cfgFile =
                ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/车辆映射表.xlsx");

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

            if(row.getCell(COL_NUM_ALARMPRO) == null
                    ||row.getCell(COL_NUM_ALARMPRO).equals("0")
            || row.getCell(COL_NUM_ALARMPRO).toString().length() == 0){
                //alarm pro 为 0 或为空，不需要做报警判断

                continue;
            }

            if(!row.getCell(COL_NUM_DATATYPE).toString().equals("DI")){
                //当前全是DI
                continue;
            }

            TrainAlarmCfgModel trainAlarmCfgModel
                    = new TrainAlarmCfgModel();

            trainAlarmCfgModel.setLine(row.getCell(COL_NUM_LINE).toString());
            trainAlarmCfgModel.setTrain(row.getCell(COL_NUM_TRAIN).toString());
            trainAlarmCfgModel.setVarName(row.getCell(COL_NUM_VARNAME).toString());
            trainAlarmCfgModel.setEquipType(row.getCell(COL_NUM_EQUIPTYPE).toString());
            trainAlarmCfgModel.setAlarmContent(row.getCell(COL_NUM_ALARMCONTENT)!=null?row.getCell(COL_NUM_ALARMCONTENT).toString():"");
            trainAlarmCfgModel.setAlarmLevel(row.getCell(COL_NUM_ALARMLEVEL).toString());

            trainAlarmCfgModelList.add(trainAlarmCfgModel);
        }
    }
}
