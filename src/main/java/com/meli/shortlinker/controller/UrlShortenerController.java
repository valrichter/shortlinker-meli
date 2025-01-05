package com.meli.shortlinker.controller;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Url-Shortener", description = "Shorten and resolve URLs")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Operation(summary = "Service health check")
    @GetMapping("/health")
    public String healthCheck() {
        return "Service is up and running over HTTP!";
    }

    @PostMapping("/create-url")
    public Url createShortUrl(@RequestBody UrlDto urlDto) {
        return urlShortenerService.createShortUrl(urlDto);
    }

    @GetMapping("/resolve-url")
    public Map<String, String> resolveShortUrl(@RequestParam String shortUrl) {
        String longUrl = urlShortenerService.getLongUrl(shortUrl);
        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", shortUrl);
        response.put("longUrl", longUrl);
        return response;
    }

    @GetMapping("/cached-urls")
    public Set<String> getAllUrls() {
        return urlShortenerService.getAllUrls();
    }
}