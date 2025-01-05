package com.meli.shortlinker.repository;

import java.util.Map;
import java.util.Set;

public interface UrlCacheRepository {
    String saveShortUrl(String shortUrl, String longUrl);
    String getLongUrl(String shortUrl);
    Map<String, String> getAllUrls();
    String deleteShortUrl(String shortUrl);
}