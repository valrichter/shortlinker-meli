package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface UrlShortenerService {
    Url createShortUrl(UrlDto urlDto) throws NoSuchAlgorithmException;
    String getLongUrl(String shortUrl);
    Map<String, String> getAllUrlsFromCache();
    List<UrlDto> getAllUrls();
}
