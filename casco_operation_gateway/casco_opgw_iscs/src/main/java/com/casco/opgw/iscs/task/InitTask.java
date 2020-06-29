package com.casco.opgw.iscs.task;

import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.iscs.OpISCSApplication;
import com.casco.opgw.iscs.entity.VariableEntity;
import com.casco.opgw.iscs.utils.IscsUtils;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Order(1)
public class InitTask implements CommandLineRunner {

    private final static int COL_NUM_EQUIPTYPE        = 3;
    private final static int COL_NUM_VARTYPE          = 7;
    private final static int COL_NUM_VARNAME          = 15;//13
    private final static int COL_NUM_IOTYPE           = 8;
    private final static int COL_NUM_PORT             = 16;//14
    private final static int COL_NUM_MODBUSADDR       = 18;//16
    private final static int COL_NUM_REGADDR          = 19;//17

    //存储变量信息
    public static List<VariableEntity> variableEntityList
            = new ArrayList<>();

    //全部寄存器地址
    public static List<Integer> modbusAddr = new ArrayList<>();

    //缓存水泵的状态
    public static ConcurrentHashMap<String, Object> cachemap
            = new ConcurrentHashMap<>();

    private static final String config_path
            = "./srvconfig/";

    @Override
    public void run(String... args) throws Exception {

        XSSFWorkbook wb = null;
        File cfgFile =
                ResourceUtils.getFile(config_path
                        + OpISCSApplication.global_params.get(ParamConstant.PARAM_KEY_SRVCONFIG));

        InputStream in = new FileInputStream(cfgFile);
        wb = new XSSFWorkbook(in);

        XSSFSheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {
            if(row.getRowNum() == 0){
                continue;
            }

            if(row.getCell(0) == null ||
                    row.getCell(0).toString().length() == 0){
                break;
            }



            VariableEntity variableEntity = new VariableEntity();
            variableEntity.setEquipType(row.getCell(COL_NUM_EQUIPTYPE).toString());
            variableEntity.setVarName(row.getCell(COL_NUM_VARNAME).toString());
            variableEntity.setVarType(row.getCell(COL_NUM_VARTYPE).toString());
            variableEntity.setIoType(row.getCell(COL_NUM_IOTYPE).toString());
            variableEntity.setModbusAddr(row.getCell(COL_NUM_MODBUSADDR).toString());
            Integer addr = IscsUtils.getStringToNumWithNoDecimal(row.getCell(COL_NUM_MODBUSADDR).toString());

            if(!modbusAddr.contains(addr)){
                modbusAddr.add(addr);
            }

            variableEntity.setPort(row.getCell(COL_NUM_PORT).toString());

            if(row.getCell(COL_NUM_IOTYPE).toString().equals("bit")){
                variableEntity.setRegAddr(row.getCell(COL_NUM_REGADDR).toString());
            }

            variableEntityList.add(variableEntity);

        }
    }
}
