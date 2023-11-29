package com.sd.sistemasd.utils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;


import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig())
                .transactionAware()
                .build();

        return cacheManager;
    }

    private RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration
                .defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .entryTtl( Duration.ofMinutes(Setting.TTL_MINUTES));
    }

    @Bean
    public CacheManager cacheManagerNoTTL(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfigWithoutTTL())
                .transactionAware()
                .build();

        return cacheManager;
    }

    private RedisCacheConfiguration defaultCacheConfigWithoutTTL() {
        return RedisCacheConfiguration
                .defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                .entryTtl( Duration.ZERO);
    }
}

