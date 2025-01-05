package com.meli.shortlinker.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class RedisCacheRepositoryTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisUrlCacheRepository redisCacheRepository;

    private String shortUrl;
    private String longUrl;

    @BeforeEach
    void setUp() {
        shortUrl = "short123";
        longUrl = "http://long-url.com";

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void saveShortUrlAndLongUrl_ShouldSaveSuccessfully() {
        Mockito.doNothing().when(valueOperations).set(shortUrl, longUrl);

        String result = redisCacheRepository.saveShortUrlAndLongUrl(shortUrl, longUrl);

        assertEquals(shortUrl, result);
        Mockito.verify(valueOperations).set(shortUrl, longUrl);
    }

    @Test
    void findLongUrlByShortUrl_ShouldReturnLongUrl() {
        Mockito.when(valueOperations.get(shortUrl)).thenReturn(longUrl);

        String result = redisCacheRepository.findLongUrlByShortUrl(shortUrl);

        assertEquals(longUrl, result);
        Mockito.verify(valueOperations).get(shortUrl);
    }

    @Test
    void findAll_ShouldReturnAllUrls() {
        Map<String, String> redisData = new HashMap<>();
        redisData.put(shortUrl, longUrl);

        Set<String> keys = Set.of(shortUrl);
        Mockito.when(redisTemplate.keys("*")).thenReturn(keys);
        Mockito.when(valueOperations.get(shortUrl)).thenReturn(longUrl);

        Map<String, String> result = redisCacheRepository.findAll();

        assertEquals(redisData, result);
        Mockito.verify(redisTemplate).keys("*");
        Mockito.verify(valueOperations).get(shortUrl);
    }

}
