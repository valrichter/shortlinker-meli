package com.meli.shortlinker.config;

import com.meli.shortlinker.repository.RedisUrlCacheRepository;
import com.meli.shortlinker.repository.UrlCacheRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CacheConfigTest {

    @Autowired
    private UrlCacheRepository urlCacheRepository;

    @Test
    public void testCacheRepositoryBean() {
        assertNotNull(urlCacheRepository); // Verifica que el bean esta disponible
        assertTrue(urlCacheRepository instanceof RedisUrlCacheRepository);
    }
}
