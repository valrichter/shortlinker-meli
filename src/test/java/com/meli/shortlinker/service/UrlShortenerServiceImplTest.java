package com.meli.shortlinker.service;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.repository.UrlCacheRepository;
import com.meli.shortlinker.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceImplTest {

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerServiceImpl;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlCacheRepository urlCacheRepository;

    private UrlDto urlDto;
    private Url url;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlDto = UrlDto.builder()
                .userId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .shortUrl("https://www.mercadolibre.com")
                .longUrlProtocol("https")
                .longUrlDomain("www.mercadolibre.com")
                .longUrlPath("/")
                .isActive(true)
                .createdAt(OffsetDateTime.parse("2025-01-05T22:21:41.852Z"))
                .statsCount(0)
                .build();
        url = Url.builder()
                .shortUrl("http://meli.ly/abc123")
                .slug("abc123")
                .userId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .longUrlProtocol("https")
                .longUrlDomain("www.mercadolibre.com")
                .longUrlPath("/")
                .isActive(true)
                .createdAt(OffsetDateTime.parse("2025-01-05T22:21:41.852Z"))
                .statsCount(0)
                .build();
    }

    @Test
    void createShortUrl_ShouldGenerateShortUrlAndSave() throws NoSuchAlgorithmException {
        String expectedSlug = "abc123";
        String expectedShortUrl = "http://meli.ly/" + expectedSlug;
        Url urlToSave = Url.builder()
                .shortUrl(expectedShortUrl)
                .slug(expectedSlug)
                .userId(urlDto.getUserId())
                .longUrlProtocol(urlDto.getLongUrlProtocol())
                .longUrlDomain(urlDto.getLongUrlDomain())
                .longUrlPath(urlDto.getLongUrlPath())
                .isActive(urlDto.isActive())
                .createdAt(urlDto.getCreatedAt())
                .statsCount(urlDto.getStatsCount())
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(urlToSave);

        Url result = urlShortenerServiceImpl.createShortUrl(urlDto);

        assertNotNull(result);
        assertEquals(expectedShortUrl, result.getShortUrl());
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void getLongUrl_ShouldReturnCachedUrl() {
        String shortUrl = "http://meli.ly/abc123";
        String cachedLongUrl = "https://www.mercadolibre.com";
        when(urlCacheRepository.findLongUrlByShortUrl(shortUrl)).thenReturn(cachedLongUrl);

        String result = urlShortenerServiceImpl.getLongUrl(shortUrl);

        assertEquals("REDIS CACHED URL: " + cachedLongUrl, result);
        verify(urlCacheRepository, times(1)).findLongUrlByShortUrl(shortUrl);
    }

    @Test
    void getLongUrl_ShouldReturnUrlNotFound() {
        String shortUrl = "http://meli.ly/nonexistent";
        when(urlCacheRepository.findLongUrlByShortUrl(shortUrl)).thenReturn(null);
        when(urlRepository.findById(shortUrl)).thenReturn(Optional.empty());

        String result = urlShortenerServiceImpl.getLongUrl(shortUrl);

        assertEquals("URL NOT FOUND: " + shortUrl, result);
        verify(urlCacheRepository, times(1)).findLongUrlByShortUrl(shortUrl);
        verify(urlRepository, times(1)).findById(shortUrl);
    }

    @Test
    void getLongUrl_ShouldReturnUrlNotActive() {
        String shortUrl = "http://meli.ly/abc123";
        url.setActive(false);
        when(urlCacheRepository.findLongUrlByShortUrl(shortUrl)).thenReturn(null);
        when(urlRepository.findById(shortUrl)).thenReturn(Optional.of(url));

        String result = urlShortenerServiceImpl.getLongUrl(shortUrl);

        assertEquals("URL NOT ACTIVE: " + shortUrl, result);
        verify(urlCacheRepository, times(1)).findLongUrlByShortUrl(shortUrl);
        verify(urlRepository, times(1)).findById(shortUrl);
    }

    @Test
    void getLongUrl_ShouldReturnSavedUrl() {
        String shortUrl = "http://meli.ly/abc123";
        String longUrl = "https://www.mercadolibre.com/";
        when(urlCacheRepository.findLongUrlByShortUrl(shortUrl)).thenReturn(null); // No está en cache
        when(urlRepository.findById(shortUrl)).thenReturn(Optional.of(url));

        String result = urlShortenerServiceImpl.getLongUrl(shortUrl);

        assertEquals("POSTGRES SAVED URL: " + longUrl, result);
        verify(urlCacheRepository, times(1)).findLongUrlByShortUrl(shortUrl);
        verify(urlRepository, times(1)).findById(shortUrl);
        verify(urlCacheRepository, times(1)).saveShortUrlAndLongUrl(shortUrl, longUrl);
    }
}