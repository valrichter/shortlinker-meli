package com.meli.shortlinker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCacheRepository implements UrlCacheRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String saveShortUrl(String shortUrl, String longUrl) {
        redisTemplate.opsForValue().set(shortUrl, longUrl);
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return redisTemplate.opsForValue().get(shortUrl);
    }

    @Override
    public String deleteShortUrl(String shortUrl) {
        if (redisTemplate.delete(shortUrl))
            return shortUrl;
       return "url not found";
    }
}

