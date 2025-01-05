package com.meli.shortlinker.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "urls")
public class Url {
    @Setter
    @Id
    @Column(name = "short_url", nullable = false, length = 30)
    private String shortUrl;
    @Setter
    @Column(name = "slug", nullable = false, length = 8, unique = true)
    private String slug;
    @Setter
    @Column(name = "user_id", nullable = true)
    private UUID userId;
    @Setter
    @Column(name = "long_url_protocol", nullable = false, length = 10)
    private String longUrlProtocol;
    @Setter
    @Column(name = "long_url_domain", nullable = false, length = 90)
    private String longUrlDomain;
    @Setter
    @Column(name = "long_url_path", nullable = false, length = 400)
    private String longUrlPath;
    @Setter
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive = true;
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone default now()")
    private java.time.OffsetDateTime createdAt;
    @Setter
    @Column(name = "stats_count", nullable = false, columnDefinition = "integer default 0")
    private int statsCount = 0;
}