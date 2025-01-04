package com.meli.shortlinker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.meli.shortlinker.service.UrlShortenerService;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> createShortUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("longUrl");
        String shortUrl = urlShortenerService.createShortUrl(longUrl);
        Map<String, String> response = new HashMap<>();
        response.put("longUrl", longUrl);
        response.put("shortUrl", shortUrl);
        return response;
    }

    // Resolver una URL corta
//    @GetMapping("/{shortUrl}")
//    public Map<String, String> resolveShortUrl(@PathVariable String shortUrl) {
//        String longUrl = urlShortenerService.getLongUrl(shortUrl);
//        Map<String, String> response = new HashMap<>();
//        response.put("shortUrl", shortUrl);
//        response.put("longUrl", longUrl);
//        return response;
//    }
}