package com.meli.shortlinker.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UrlDto {
    @NotBlank(message = "La URL corta es obligatoria y no puede estar vacía")
    private String shortUrl;
    @NotBlank(message = "El slug es obligatorio y no puede estar vacío")
    private String slug;
    private UUID userId;
    @NotBlank(message = "El protocolo de la URL larga es obligatorio y no puede estar vacío")
    private String longUrlProtocol;
    @NotBlank(message = "El dominio de la URL larga es obligatorio y no puede estar vacío")
    private String longUrlDomain;
    @NotBlank(message = "La ruta de la URL larga es obligatoria y no puede estar vacía")
    private String longUrlPath;
    @JsonProperty(value = "active", defaultValue = "true")
    private boolean isActive;
    @JsonIgnore
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @JsonIgnore
    private int statsCount;

    public String getLongUrl() {
        return longUrlProtocol + "://" + longUrlDomain + longUrlPath;
    }
}