package com.meli.shortlinker.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UrlDto {
    private String shortUrl;
    private String slug;
    private UUID userId;
    private String longUrlProtocol;
    private String longUrlDomain;
    private String longUrlPath;
    @JsonProperty("active")
    private boolean isActive;
    private OffsetDateTime createdAt;
    private int statsCount;

    public String getLongUrl() {
        return longUrlProtocol + "://" + longUrlDomain + longUrlPath;
    }
}