package com.casco.opgw.iscs.config;

import com.casco.opgw.iscs.entity.VariableEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
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
public class InitParamTask implements CommandLineRunner {

    private static int COL_NUM_VARNAME       = 12;
    private static int COL_NUM_PORT          = 2;
    private static int COL_NUM_MODBUSADDR    = 3;
    private static int COL_NUM_REGADDR       = 4;
    private static int COL_NUM_DEVTYPE       = 5;
    private static int COL_NUM_ATTRTAG       = 6;
    private static int COL_NUM_DEVPARTITION  = 7;


    private static List<VariableEntity> variableEntityList
            = new ArrayList<>();


    //缓存水泵的状态
    private static ConcurrentHashMap<String, Object> cachemap
            = new ConcurrentHashMap<>();


    @Override
    public void run(String... args) throws Exception {

        XSSFWorkbook wb = null;
        File cfgFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/cdl9.xlsx");

        InputStream in = new FileInputStream(cfgFile);
        wb = new XSSFWorkbook(in);

        XSSFSheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {

            if(row.getRowNum() == 0){
                continue;
            }

            if(row.getCell(0) == null ||
                    row.getCell(0).toString().length() == 0){

                System.out.println("InitParamTask end: row num " + row.getRowNum());

                return;
            }

            VariableEntity variableEntity = new VariableEntity();
            variableEntity.setVarName(row.getCell(InitParamTask.COL_NUM_VARNAME).toString());

            variableEntityList.add(variableEntity);

        }
    }
}
