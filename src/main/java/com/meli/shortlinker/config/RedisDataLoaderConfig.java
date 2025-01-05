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
            redisTemplate.opsForValue().set("meli.ly/25years", "https://www.mercadolibre.com.ar/");
            redisTemplate.opsForValue().set("meli.ly/rfvt9876", "https://www.linkedin.com/in/valrichter'");
            redisTemplate.opsForValue().set("meli.ly/uvwx3456", "https://www.github.com/valrichter");
            System.out.println("Initial data loaded into Redis");
        };
    }
}
