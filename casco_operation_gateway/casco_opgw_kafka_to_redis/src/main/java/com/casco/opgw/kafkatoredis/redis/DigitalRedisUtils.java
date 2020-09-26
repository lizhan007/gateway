package com.casco.opgw.kafkatoredis.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
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

    /**
     * redis 批量写入
     * @param maps
     */
    public void batchSet(Map<byte[],byte[]> maps){
        List result = stringRedisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();

                if(maps.size() == 0 || null == maps){
                    return null;
                }

                connection.mSet(maps);
                for(byte[] key:maps.keySet()){
                    connection.expire(key,EXPIRE_TIME_MINITES*3600);
                }
                connection.closePipeline();
                return null;
            }
        });
    }
}
