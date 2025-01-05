package com.meli.shortlinker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisDataLoaderConfig {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean
    public CommandLineRunner loadInitialData() {
        return args -> {
            redisTemplate.opsForValue().set("app_version", "1.0.0");
            redisTemplate.opsForValue().set("app_name", "UrlShortener");
            redisTemplate.opsForValue().set("redis_initialized", "true");
            redisTemplate.opsForValue().set("short.ly", "http://short.ly/");

            System.out.println("Initial data loaded into Redis");
        };
    }
}
