package com.casco.opgw.kafkatoinfluxdb.task;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.kafkatoinfluxdb.influxdb.DataCache;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshTask {

    @Resource(name = "SIG")
    private InfluxDB SigInfluxDB;

    @Resource(name = "BAS")
    private InfluxDB BasInfluxDB;

    @Resource(name = "TRAIN")
    private InfluxDB TrainInfluxDB;

    @Scheduled(cron = "0 */1 * * * ?")
    public void refreshDigitValue() {

        refreshSigDigitMessage();

        refreshScsiDigitMessage();

        refreshTrainDigitMessage();

    }


    private void refreshSigDigitMessage(){

        System.out.println("refreshSigDigitMessage  " + DataCache.sigPointCache.size());

        BatchPoints batchPoints
                = BatchPoints.database("SIG").retentionPolicy("52w").build();

        for(DigitMessage digitMessage: DataCache.sigPointCache){

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                return;
            }
            Point.Builder builder = Point.measurement("SIG_DIGIT");
            String dstr = String.valueOf(new Date().getTime());
            Long time = Long.valueOf(dstr.substring(0,dstr.length() - 3));
            builder.time(digitMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("region",digitMessage.getRegionTag());
            builder.tag("srcId",digitMessage.getSrcIdTag());
            builder.tag("type",digitMessage.getTypeTag());
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();
            DataCache.addPoint(DataCache.SIG_TYPE, digitMessage);
            batchPoints.point(point);
        }

        SigInfluxDB.setDatabase("SIG").setRetentionPolicy("52w").write(batchPoints);
    }

    private void refreshScsiDigitMessage(){

        System.out.println("refreshScsiDigitMessage  " + DataCache.scsiPointCache.size());

        BatchPoints batchPoints
                = BatchPoints.database("BAS").retentionPolicy("52w").build();

        for(DigitMessage digitMessage: DataCache.scsiPointCache){

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                return;
            }
            Point.Builder builder = Point.measurement("SIG_DIGIT");
            String dstr = String.valueOf(new Date().getTime());
            Long time = Long.valueOf(dstr.substring(0,dstr.length() - 3));
            builder.time(digitMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("region",digitMessage.getRegionTag());
            builder.tag("srcId",digitMessage.getSrcIdTag());
            builder.tag("type",digitMessage.getTypeTag());
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();
            DataCache.addPoint(DataCache.SIG_TYPE, digitMessage);
            batchPoints.point(point);
        }

        BasInfluxDB.setDatabase("BAS").setRetentionPolicy("52w").write(batchPoints);
    }

    private void refreshTrainDigitMessage(){

        System.out.println("refreshTrainDigitMessage  " + DataCache.trainPointCache.size());

        BatchPoints batchPoints
                = BatchPoints.database("TRAIN").retentionPolicy("52w").build();

        for(DigitMessage digitMessage: DataCache.trainPointCache){

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){
                return;
            }
            Point.Builder builder = Point.measurement("SIG_DIGIT");
            String dstr = String.valueOf(new Date().getTime());
            Long time = Long.valueOf(dstr.substring(0,dstr.length() - 3));
            builder.time(digitMessage.getTimestamp(),TimeUnit.MILLISECONDS);
            builder.addField("value",digitMessage.getValue());
            builder.tag("line", digitMessage.getLineTag());
            builder.tag("region",digitMessage.getRegionTag());
            builder.tag("srcId",digitMessage.getSrcIdTag());
            builder.tag("type",digitMessage.getTypeTag());
            builder.tag("pointcode",digitMessage.getPointcodeTag());
            Point point = builder.build();
            DataCache.addPoint(DataCache.SIG_TYPE, digitMessage);
            batchPoints.point(point);
        }

        TrainInfluxDB.setDatabase("TRAIN").setRetentionPolicy("52w").write(batchPoints);
    }
}
