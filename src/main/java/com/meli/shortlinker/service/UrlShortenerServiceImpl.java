package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.repository.UrlCacheRepository;
import com.meli.shortlinker.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final String URL_PREFIX = "http://meli.ly/";

    @Autowired
    private UrlCacheRepository urlCacheRepository;
    @Autowired
    private UrlRepository urlRepository;

        public Url createShortUrl(UrlDto urlDto) {
            String slug = UUID.randomUUID().toString().substring(0, 8);
            String shortUrlGenerated = URL_PREFIX + slug;
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

            urlCacheRepository.saveShortUrl(shortUrlGenerated, longUrl);
            return urlRepository.save(url);
        }

    public String getLongUrl(String shortUrl) {
        String redisUrl = urlCacheRepository.getLongUrl(shortUrl);
        Url postgresUrl = urlRepository.findById(shortUrl).orElse(null);
        assert postgresUrl != null;
        String postgresUrlLong = postgresUrl.getLongUrlProtocol() + "://" + postgresUrl.getLongUrlDomain() + postgresUrl.getLongUrlPath();

        return "Redis: " + redisUrl + " - Postgres: " + postgresUrlLong;
    }

    public Map<String, String> getAllUrlsFromCache() {
        return urlCacheRepository.getAllUrls();
    }
}
