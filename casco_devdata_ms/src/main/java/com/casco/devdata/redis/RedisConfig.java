package com.casco.devdata.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;


@Component
public class RedisConfig {


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean(name = "DigitalRedisTemplate")
    public StringRedisTemplate redisDigitalTemplate() {
        return buildRedisTemplate(buildConnectionFactory(jedisPoolConfig(), 0));
    }

    @Bean(name = "EnumRedisTemplate")
    public StringRedisTemplate redisEnumTemplate() {
        return buildRedisTemplate(buildConnectionFactory(jedisPoolConfig(), 1));
    }

    @Bean(name = "AnalogRedisTemplate")
    public StringRedisTemplate redisAnalogTemplate() {
        return buildRedisTemplate(buildConnectionFactory(jedisPoolConfig(), 2));
    }


    protected StringRedisTemplate buildRedisTemplate(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(100);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(20);
        //当池内没有可用的连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(10000);


        return jedisPoolConfig;
    }

    public JedisConnectionFactory buildConnectionFactory(JedisPoolConfig jedisPoolConfig, int database) {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(redisHost);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(database);

        //redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(redisPort);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisBuilder.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jedisBuilder.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);

    }
}