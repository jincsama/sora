package com.sora.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisCommandFactory){
        log.info("创建redis模板对象");
        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(redisCommandFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
