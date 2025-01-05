package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.repository.UrlCacheRepository;
import com.meli.shortlinker.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final String DOMAIN_NAME = "http://meli.ly/";

    @Autowired
    private UrlCacheRepository urlCacheRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url createShortUrl(UrlDto urlDto) {
        String slug = UUID.randomUUID().toString().substring(0, 8);
        String shortUrlGenerated = DOMAIN_NAME + slug;
        String longUrl = urlDto.getLongUrl();

        Url url = Url.builder()
                .shortUrl(shortUrlGenerated)
                .slug(slug)
                .userId(urlDto.getUserId())
                .longUrlProtocol(urlDto.getLongUrlProtocol())
                .longUrlDomain(urlDto.getLongUrlDomain())
                .longUrlPath(urlDto.getLongUrlPath())
                .isActive(urlDto.isActive())
                .statsCount(urlDto.getStatsCount())
                .build();

        urlCacheRepository.saveShortUrlAndLongUrl(shortUrlGenerated, longUrl);
        return urlRepository.save(url);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String redisUrl = urlCacheRepository.findLongUrlByShortUrl(shortUrl);
        Url postgresUrl = urlRepository.findById(shortUrl).orElse(null);
        assert postgresUrl != null;
        String postgresUrlLong = postgresUrl.getLongUrlProtocol() + "://" + postgresUrl.getLongUrlDomain() + postgresUrl.getLongUrlPath();

        return "Redis: " + redisUrl + " - Postgres: " + postgresUrlLong;
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
