package com.meli.shortlinker.repository;

public interface UrlCacheRepository {
    String saveShortUrl(String shortUrl, String longUrl);
    String getLongUrl(String shortUrl);
    String deleteShortUrl(String shortUrl);
}