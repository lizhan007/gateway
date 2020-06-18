package com.casco.opgw.kafkatoredis.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


@Component
public class EnumRedisUtils {

    @Resource(name = "EnumRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public void publish(String channel, String message){
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
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
