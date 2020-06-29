package com.casco.opgw.train.task;

import Ice.ObjectAdapter;
import SSIP.NEWS.*;
import SSIP.dms_factoryPrx;
import SSIP.dms_factoryPrxHelper;
import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.train.OpgwTrainApplication;
import com.casco.opgw.train.observer.IceAnalogObserver;
import com.casco.opgw.train.observer.IceDigitObserver;
import com.casco.opgw.train.utils.PointCodeUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class InitPTask implements CommandLineRunner {

    //数字量存储
    public static List<String> digitList = new ArrayList<>();

    //模拟量存储
    public static List<String> analogList = new ArrayList<>();

    @Value("${ice.service_name}")
    private String service_name;

    @Value("${ice.service_endpoint}")
    private String service_endpoint;

    @Value("${ice.adapter_name}")
    private String adapter_name;

    @Value("${ice.adapter_endpoint}")
    private String adapter_endpoint;


    /*-
     * jar包初始化时，生产环境下读取 ./srv_config/ 目录下配置文件
     * 1. 该文件要求为excel
     * Linux:/data/serverconfig/"
     */
    private static final String config_path
            = "./srvconfig/";

    @Override
    public void run(String... args) throws Exception {

        //1. 解析excel文件
        XSSFWorkbook wb = null;
        File cfgFile =
                ResourceUtils.getFile(config_path
                        + OpgwTrainApplication.global_params.get(ParamConstant.PARAM_KEY_SRVCONFIG));


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

            //String pointCode = PointCodeUtils.getPointCode(row.getCell(1).toString());
            String pointCode = row.getCell(1).toString();
            String type      = row.getCell(3).toString();
            //String type = PointCodeUtils.TYPE_DI;

            if(type.equals(PointCodeUtils.TYPE_DI)){
                digitList.add(pointCode);
            }else if(type.equals(PointCodeUtils.TYPE_AI)){
                analogList.add(pointCode);
            }
        }

        System.out.println("start subscribe");

        //注册服务
        Ice.Communicator communicator = null;
        try {
            communicator = Ice.Util.initialize();
            System.out.println(service_name + " : " + service_endpoint);
            Ice.ObjectPrx op = communicator.stringToProxy(service_name+":"+service_endpoint);
            dms_publisherPrx dms_publisherPrx = dms_publisherPrxHelper.checkedCast(op);

            ObjectAdapter objectAdapter =  communicator.
                    createObjectAdapterWithEndpoints(adapter_name, adapter_endpoint);

            //订阅数字量
            IceDigitObserver iceDigitObserver = new IceDigitObserver();



            dms_observerPrx dms_digit_observerPrx
                    = dms_observerPrxHelper.uncheckedCast(objectAdapter.addWithUUID(iceDigitObserver));

            Boolean res = dms_publisherPrx.unsubscribe(dms_digit_observerPrx, 1);

            objectAdapter.add(iceDigitObserver, communicator.stringToIdentity("digit_observer"));
            objectAdapter.activate();

            MetaTopic digit_topic         = new MetaTopic();
            digit_topic.no                = 1; //订阅号 1~127
            digit_topic.domain            = "LP-60913";
            digit_topic.topicname         = "SSIP";
            digit_topic.user              = "sa";
            digit_topic.password          = "root";
            digit_topic.period            = 1;

            MetaPara[] digit_metaPara     = new MetaPara[digitList.size()];

            for(int i = 0; i < digitList.size(); i++) {
                digit_metaPara[i] = new MetaPara();
                digit_metaPara[i].pointcode = digitList.get(i);
                digit_metaPara[i].table       = "digital";
                digit_metaPara[i].field       = "Value";
                digit_metaPara[i].index       = i;
                digit_metaPara[i].type        = 7;
            }

            res = dms_publisherPrx.subscribe(dms_digit_observerPrx, digit_topic, digit_metaPara);
            System.out.println("dms_publisherPrx.subscribe(dms_digit_observerPrx:  " + res);

            //订阅模拟量
            IceAnalogObserver iceAnalogObserver = new IceAnalogObserver();

            dms_observerPrx dms_analog_observerPrx
                    = dms_observerPrxHelper.uncheckedCast(objectAdapter.addWithUUID(iceAnalogObserver));

            res = dms_publisherPrx.unsubscribe(dms_analog_observerPrx, 2);

            objectAdapter.add(iceDigitObserver, communicator.stringToIdentity("analog_observer"));
            objectAdapter.activate();

            MetaTopic anaog_topic         = new MetaTopic();
            anaog_topic.no                = 2; //订阅号 1~127
            anaog_topic.domain            = "LP-60913";
            anaog_topic.topicname         = "SSIP";
            anaog_topic.user              = "sa";
            anaog_topic.password          = "root";
            anaog_topic.period            = 1;

            MetaPara[] analog_metaPara     = new MetaPara[analogList.size()];

            for(int i = 0; i < analogList.size(); i++) {

                analog_metaPara[i] = new MetaPara();

                analog_metaPara[i].pointcode = analogList.get(i);
                analog_metaPara[i].table       = "analog";
                analog_metaPara[i].field       = "Value";
                analog_metaPara[i].index       = i;
                analog_metaPara[i].type        = 10;
            }

            res = dms_publisherPrx.subscribe(dms_analog_observerPrx, anaog_topic, analog_metaPara);
            System.out.println("dms_publisherPrx.subscribe(dms_analog_observerPrx:  " + res);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
