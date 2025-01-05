package com.meli.shortlinker.repository;

import java.util.Set;

public interface UrlCacheRepository {
    String saveShortUrl(String shortUrl, String longUrl);
    String getLongUrl(String shortUrl);
    String deleteShortUrl(String shortUrl);
    Set<String> getAllUrls();
}