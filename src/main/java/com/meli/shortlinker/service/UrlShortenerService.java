package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;

import java.util.Set;

public interface UrlShortenerService {
    Url createShortUrl(UrlDto urlDto);
    String getLongUrl(String shortUrl);
    Set<String> getAllUrls();
}
