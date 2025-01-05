package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.repository.UrlCacheRepository;
import com.meli.shortlinker.repository.UrlRepository;
import com.meli.shortlinker.util.SlugGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final String DOMAIN_NAME = "http://meli.ly/";

    @Autowired
    private UrlCacheRepository urlCacheRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url createShortUrl(UrlDto urlDto) throws NoSuchAlgorithmException {
        String slug = SlugGenerator.generate(urlDto.getLongUrl(), urlDto.getUserId(), String.valueOf(urlDto.getCreatedAt()));
        String shortUrlGenerated = DOMAIN_NAME + slug;

        Url url = Url.builder()
                .shortUrl(shortUrlGenerated)
                .slug(slug)
                .userId(urlDto.getUserId())
                .longUrlProtocol(urlDto.getLongUrlProtocol())
                .longUrlDomain(urlDto.getLongUrlDomain())
                .longUrlPath(urlDto.getLongUrlPath())
                .isActive(urlDto.isActive())
                .createdAt(urlDto.getCreatedAt())
                .statsCount(urlDto.getStatsCount())
                .build();

        String longUrl = urlDto.getLongUrl();
        urlCacheRepository.saveShortUrlAndLongUrl(shortUrlGenerated, longUrl);
        return urlRepository.save(url);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String cachedLongUrl = urlCacheRepository.findLongUrlByShortUrl(shortUrl);
        if (cachedLongUrl != null) {
            return "REDIS CACHED URL: " + cachedLongUrl;
        }

        Url url = urlRepository.findById(shortUrl).orElse(null);
        if (url == null ) {
            return "URL NOT FOUND: " + shortUrl;
        }
        if (!url.isActive()) {
            return "URL NOT ACTIVE: " + shortUrl;
        }

        String longUrl = url.getLongUrl();
        urlCacheRepository.saveShortUrlAndLongUrl(shortUrl, longUrl);
        return "POSTGRES SAVED URL: " + longUrl;
    }

    @Override
    public Map<String, String> getAllUrlsFromCache() {
        return urlCacheRepository.findAll();
    }

    @Override
    public List<UrlDto> getAllUrls() {
        List<Url> urls = urlRepository.findAll();
        return urls.stream().map(url -> UrlDto.builder()
                .shortUrl(url.getShortUrl())
                .slug(url.getSlug())
                .userId(url.getUserId())
                .longUrlProtocol(url.getLongUrlProtocol())
                .longUrlDomain(url.getLongUrlDomain())
                .longUrlPath(url.getLongUrlPath())
                .isActive(url.isActive())
                .createdAt(url.getCreatedAt())
                .statsCount(url.getStatsCount())
                .build())
                .toList();
    }
}
