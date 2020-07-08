package com.casco.opgw.kafkatoredis.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class DigitalRedisUtils {

    //默认5分钟
    @Value("${spring.kafka.expire:5}")
    private int EXPIRE_TIME_MINITES;

    @Resource(name = "DigitalRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public void publish(String channel, String message){
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public boolean set(String key, String value) {
        try {
            //stringRedisTemplate.opsForValue().set(key, value);
            stringRedisTemplate.opsForValue().set(key, value,EXPIRE_TIME_MINITES,  TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sets(Map<String, String> params){
        try{
            stringRedisTemplate.opsForValue().multiSet(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
