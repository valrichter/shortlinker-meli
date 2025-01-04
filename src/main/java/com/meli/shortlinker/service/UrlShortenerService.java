package com.meli.shortlinker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private static final String URL_PREFIX = "http://short.ly/";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String createShortUrl(String longUrl) {
        String shortUrl = URL_PREFIX + UUID.randomUUID().toString().substring(0, 8);
        redisTemplate.opsForValue().set(shortUrl, longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        return redisTemplate.opsForValue().get(shortUrl);
    }
}
