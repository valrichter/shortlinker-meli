package com.meli.shortlinker.controller;

import com.meli.shortlinker.dto.UrlDto;
import com.meli.shortlinker.model.Url;
import com.meli.shortlinker.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Url-Shortener", description = "Shorten and resolve URLs")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @Operation(summary = "Service health check")
    @GetMapping("/health")
    public String healthCheck() {
        return "Service is up and running over HTTP!";
    }

    @Operation(summary = "Create short URL")
    @PostMapping("/urls")
    public Url createShortUrl(@RequestBody @Valid UrlDto urlDto) {
        return urlShortenerService.createShortUrl(urlDto);
    }

    @Operation(summary = "Redirect to long URL")
    @GetMapping("/urls/resolve") // Reemplazar por @PathVariable "/urls/{shortUrl}" (no se encontro solucion)
    public ResponseEntity<String> resolveShortUrl(@RequestParam String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Short URL is required");
        }

        String longUrl = urlShortenerService.getLongUrl(shortUrl);
        if (longUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found");
        }

        return new ResponseEntity<>(longUrl, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all cached URLs")
    @GetMapping("/urls/cached")
    public ResponseEntity<Map<String, String>> getAllUrlsFromCache() {
        return new ResponseEntity<>(urlShortenerService.getAllUrlsFromCache(), HttpStatus.OK);
    }

    @Operation(summary = "Get all saved URLs")
    @GetMapping("/urls/saved")
    public ResponseEntity<List<UrlDto>> getAllUrls() {
        return ResponseEntity.ok(urlShortenerService.getAllUrls());
    }

}