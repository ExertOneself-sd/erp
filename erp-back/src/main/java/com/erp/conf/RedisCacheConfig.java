package com.erp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisCacheConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /*创建CacheManager对象*/
    @Bean
    public CacheManager cacheManager(){
        //创建封装redis缓存配置的对象
        RedisCacheConfiguration cfg = RedisCacheConfiguration.defaultCacheConfig();

        cfg=cfg.serializeValuesWith(RedisSerializationContext
                .SerializationPair.fromSerializer(RedisSerializer.json()));

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cfg).build();

    }
}
