package com.casco.opgw.kafkatoredis.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AnalogRedisUtils {

    @Resource(name = "AnalogRedisTemplate")
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
}
