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
    public void recvDigitMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);


        //缓存更新,修改redis写入为channel0
        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("0", digitMessage.getKeys());

            System.out.println(JSON.toJSONString(map));

            digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }

        //更新redis
        digitalRedisUtils.set(KeyUtils.getKey(digitMessage), digitMessage.getValue().toString());


    }


    @KafkaListener(topics = "casco_opgw_signal_enum", groupId = "casco_opgw_kafka_to_redis")
    public void recvEnumMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());

        EnumMessage enumMessage = JSON.parseObject(consumerRecord.value(), EnumMessage.class);

        //缓存更新
        if(enumMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("1", enumMessage.getKeys());



            enumRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }

        //更新redis
        enumRedisUtils.set(KeyUtils.getKey(enumMessage), enumMessage.getValue().toString());


    }

    @KafkaListener(topics = "casco_opgw_signal_analog", groupId = "casco_opgw_kafka_to_redis")
    public void recvAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());

        AnalogMessage analogMessage = JSON.parseObject(consumerRecord.value(), AnalogMessage.class);

        //缓存更新
        if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("2", analogMessage.getKeys());

            analogRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }

        //更新redis
        analogRedisUtils.set(KeyUtils.getKey(analogMessage), analogMessage.getValue().toString());

    }

    @KafkaListener(topics = "casco_opgw_train_digit", groupId = "casco_opgw_train_kafka_to_redis")
    public void recvTrainDigitMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        //缓存更新,修改redis写入为channel0

        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("3", digitMessage.getKeys());

            digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }
        //更新redis
        digitalRedisUtils.set(KeyUtils.getTrainKey(digitMessage), digitMessage.getValue().toString());
    }

    @KafkaListener(topics = "casco_opgw_train_analog", groupId = "casco_opgw_train_kafka_to_redis")
    public void recvTrainAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(consumerRecord.value());

        AnalogMessage analogMessage = JSON.parseObject(consumerRecord.value(), AnalogMessage.class);

        //缓存更新
        if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("4", analogMessage.getKeys());

            analogRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }
        //更新redis
        analogRedisUtils.set(KeyUtils.getTrainKey(analogMessage), analogMessage.getValue().toString());
    }

    @KafkaListener(topics = "casco_opgw_iscs_digit", groupId = "casco_opgw_iscs_kafka_to_redis")
    public void recvIscsDigitMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug("iscsdit " + consumerRecord.value());

        DigitMessage digitMessage = JSON.parseObject(consumerRecord.value(), DigitMessage.class);

        //缓存更新,修改redis写入为channel0
        if(digitMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("5", digitMessage.getKeys());

            digitalRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }
        //更新redis
        digitalRedisUtils.set(KeyUtils.getISCSKey(digitMessage), digitMessage.getValue().toString());
    }

    @KafkaListener(topics = "casco_opgw_iscs_analog", groupId = "casco_opgw_iscs_kafka_to_redis")
    public void recvIscsAnalogMsg(ConsumerRecord<String, String> consumerRecord){
        log.debug(" iscsanalog : " + consumerRecord.value());

        AnalogMessage analogMessage = JSON.parseObject(consumerRecord.value(), AnalogMessage.class);

        //缓存更新
        if(analogMessage.getMsgType().equals(KafkaConstant.MSG_TYPE_NOTE)){

            Map<String, List<String>> map = new HashMap<>();
            map.put("6", analogMessage.getKeys());
            analogRedisUtils.publish("channel0", JSON.toJSONString(map));
            return;
        }

        //更新redis
        analogRedisUtils.set(KeyUtils.getISCSKey(analogMessage), analogMessage.getValue().toString());
    }

}
