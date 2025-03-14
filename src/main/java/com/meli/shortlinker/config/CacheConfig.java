package com.meli.shortlinker.config;

import com.meli.shortlinker.repository.RedisUrlCacheRepository;
import com.meli.shortlinker.repository.UrlCacheRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CacheConfig {

    @Bean
    @Primary
    public UrlCacheRepository urlCacheRepository()  {
        return new RedisUrlCacheRepository();  // Proveemos la implementacion deseada, en este caso Redis
    }
}

