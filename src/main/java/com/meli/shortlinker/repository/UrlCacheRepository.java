package com.meli.shortlinker.repository;

import java.util.Map;

public interface UrlCacheRepository {
    String saveShortUrlAndLongUrl(String shortUrl, String longUrl);
    String findLongUrlByShortUrl(String shortUrl);
    Map<String, String> findAll();
    String deleteShortUrl(String shortUrl);
}