package com.meli.shortlinker.service;

import com.meli.shortlinker.repository.UrlCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlShortenerService {

    private static final String URL_PREFIX = "http://short.ly/";

    @Autowired
    private UrlCacheRepository urlCacheRepository;

    public String createShortUrl(String longUrl) {
        String shortUrl = URL_PREFIX + UUID.randomUUID().toString().substring(0, 8);
        urlCacheRepository.saveShortUrl(shortUrl, longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        return urlCacheRepository.getLongUrl(shortUrl);
    }
}
