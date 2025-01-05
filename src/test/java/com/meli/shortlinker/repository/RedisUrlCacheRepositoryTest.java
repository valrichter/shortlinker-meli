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
    private StringRedisTemplate redisTemplate;  // Mockeamos el StringRedisTemplate

    @Mock
    private ValueOperations<String, String> valueOperations;  // Mockeamos ValueOperations para los métodos opsForValue()

    @InjectMocks
    private RedisUrlCacheRepository redisCacheRepository;  // Inyectamos los mocks en la clase que estamos probando

    private String shortUrl;
    private String longUrl;

    @BeforeEach
    void setUp() {
        shortUrl = "short123";
        longUrl = "http://long-url.com";

        // Configuramos redisTemplate para devolver el mock de ValueOperations
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void saveShortUrlAndLongUrl_ShouldSaveSuccessfully() {
        // Arrange
        Mockito.doNothing().when(valueOperations).set(shortUrl, longUrl);  // Configuramos el mock para que no haga nada al invocar set()

        // Act
        String result = redisCacheRepository.saveShortUrlAndLongUrl(shortUrl, longUrl);

        // Assert
        assertEquals(shortUrl, result);  // Verificamos que se devuelva el shortUrl
        Mockito.verify(valueOperations).set(shortUrl, longUrl);  // Verificamos que el método set haya sido llamado
    }

    @Test
    void findLongUrlByShortUrl_ShouldReturnLongUrl() {
        // Arrange
        Mockito.when(valueOperations.get(shortUrl)).thenReturn(longUrl);  // Configuramos el mock para devolver longUrl cuando se llama get()

        // Act
        String result = redisCacheRepository.findLongUrlByShortUrl(shortUrl);

        // Assert
        assertEquals(longUrl, result);  // Verificamos que se devuelva el longUrl
        Mockito.verify(valueOperations).get(shortUrl);  // Verificamos que el método get haya sido llamado
    }

    @Test
    void findAll_ShouldReturnAllUrls() {
        // Arrange
        Map<String, String> redisData = new HashMap<>();
        redisData.put(shortUrl, longUrl);

        Set<String> keys = Set.of(shortUrl);
        Mockito.when(redisTemplate.keys("*")).thenReturn(keys);  // Configuramos el mock para devolver las keys
        Mockito.when(valueOperations.get(shortUrl)).thenReturn(longUrl);  // Configuramos el mock para devolver longUrl

        // Act
        Map<String, String> result = redisCacheRepository.findAll();

        // Assert
        assertEquals(redisData, result);  // Verificamos que se devuelvan todos los URLs
        Mockito.verify(redisTemplate).keys("*");  // Verificamos que se haya llamado keys()
        Mockito.verify(valueOperations).get(shortUrl);  // Verificamos que se haya llamado get() para cada key
    }

}
