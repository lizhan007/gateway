package com.casco.opgw.kafkatoredis.kafka;


import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.EnumMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.com.utils.KeyUtils;
import com.casco.opgw.kafkatoredis.redis.AnalogRedisUtils;
import com.casco.opgw.kafkatoredis.redis.DigitalRedisUtils;
import com.casco.opgw.kafkatoredis.redis.EnumRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumer {


    @Autowired
    private DigitalRedisUtils digitalRedisUtils;

    @Autowired
    private EnumRedisUtils enumRedisUtils;

    @Autowired
    private AnalogRedisUtils analogRedisUtils;

    private final ThreadPoolExecutor executor
            = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "casco_opgw_signal_digit", groupId = "casco_opgw_kafka_to_redis")
    public void recvDigitMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){
            // System.out.println(record.value());

            log.debug(record.value());

            DigitMessage digitMessage = JSON.parseObject(record.value(), DigitMessage.class);

            //缓存更新,修改redis写入为channel0
            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("0", digitMessage.getKeys());

                digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }
            //更新redis
            maps.put(KeyUtils.getKey(digitMessage).getBytes(), digitMessage.getValue().toString().getBytes());
            //digitalRedisUtils.set(KeyUtils.getKey(digitMessage), digitMessage.getValue().toString());
        }
        digitalRedisUtils.batchSet(maps);
    }


    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_kafka_to_redis")
    public void recvEnumMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){

            EnumMessage enumMessage = JSON.parseObject(record.value(), EnumMessage.class);

            //缓存更新
            if(enumMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("1", enumMessage.getKeys());

                enumRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }

            //更新redis
            maps.put(KeyUtils.getKey(enumMessage).getBytes(),enumMessage.getValue().toString().getBytes());
            //enumRedisUtils.set(KeyUtils.getKey(enumMessage), enumMessage.getValue().toString());
        }
        enumRedisUtils.batchSet(maps);
    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_kafka_to_redis")
    public void recvAnalogMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){

            AnalogMessage analogMessage = JSON.parseObject(record.value(), AnalogMessage.class);

            //缓存更新
            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("2", analogMessage.getKeys());

                analogRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }

            //更新redis
            maps.put(KeyUtils.getKey(analogMessage).getBytes(),analogMessage.getValue().toString().getBytes());
            //analogRedisUtils.set(KeyUtils.getKey(analogMessage), analogMessage.getValue().toString());
        }
        analogRedisUtils.batchSet(maps);
    }

    @KafkaListener(topics = "casco_opgw_train_digit", groupId = "casco_opgw_train_kafka_to_redis")
    public void recvTrainDigitMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){
            // System.out.println(record.value());

            //log.debug(record.value());
            DigitMessage digitMessage = JSON.parseObject(record.value(), DigitMessage.class);

            //缓存更新,修改redis写入为channel0

            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("0", digitMessage.getKeys());

                digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }
            //更新redis
            maps.put(KeyUtils.getTrainKey(digitMessage).getBytes(),digitMessage.getValue().toString().getBytes());
            //digitalRedisUtils.set(KeyUtils.getTrainKey(digitMessage), digitMessage.getValue().toString());
        }
        digitalRedisUtils.batchSet(maps);
    }

    @KafkaListener(topics = "casco_opgw_train_analog", groupId = "casco_opgw_train_kafka_to_redis")
    public void recvTrainAnalogMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){
            log.debug(record.value());
            AnalogMessage analogMessage = JSON.parseObject(record.value(), AnalogMessage.class);

            //缓存更新
            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("2", analogMessage.getKeys());

                analogRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }
            //更新redis
            maps.put(KeyUtils.getTrainKey(analogMessage).getBytes(),analogMessage.getValue().toString().getBytes());
            //analogRedisUtils.set(KeyUtils.getTrainKey(analogMessage), analogMessage.getValue().toString());
        }
        analogRedisUtils.batchSet(maps);
    }

    @KafkaListener(topics = "casco_opgw_iscs_digit", groupId = "casco_opgw_iscs_kafka_to_redis")
    public void recvIscsDigitMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){
            // System.out.println(record.value());

            log.debug("iscsdit " + record.value());

            DigitMessage digitMessage = JSON.parseObject(record.value(), DigitMessage.class);

            //缓存更新,修改redis写入为channel0
            if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("0", digitMessage.getKeys());

                digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }
            //更新redis
            maps.put(KeyUtils.getISCSKey(digitMessage).getBytes(),digitMessage.getValue().toString().getBytes());
            //digitalRedisUtils.set(KeyUtils.getISCSKey(digitMessage), digitMessage.getValue().toString());
        }
        digitalRedisUtils.batchSet(maps);
    }

    @KafkaListener(topics = "casco_opgw_iscs_analog", groupId = "casco_opgw_iscs_kafka_to_redis")
    public void recvIscsAnalogMsg(List<ConsumerRecord<String, String>> consumerRecord){

        Map<byte[],byte[]> maps = new HashMap<byte[], byte[]>();

        for(ConsumerRecord<String, String> record:consumerRecord){
            log.debug(" iscsanalog : " + record.value());
            AnalogMessage analogMessage = JSON.parseObject(record.value(), AnalogMessage.class);

            //缓存更新
            if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

                Map<String, List<String>> map = new HashMap<>();
                map.put("2", analogMessage.getKeys());
                analogRedisUtils.publish("channel0", JSON.toJSONString(map));
                continue;
            }

            //更新redis
            maps.put(KeyUtils.getISCSKey(analogMessage).getBytes(),analogMessage.getValue().toString().getBytes());
            //analogRedisUtils.set(KeyUtils.getISCSKey(analogMessage), analogMessage.getValue().toString());
        }
        analogRedisUtils.batchSet(maps);
    }

}
