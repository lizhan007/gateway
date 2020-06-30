package com.casco.devdata.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
public class DigitalRedisUtils {

    @Resource(name = "DigitalRedisTemplate")
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

    public List<String> gets(List<String> keys){
        try{
            System.err.println(keys);

            return stringRedisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
