package com.meli.shortlinker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Url {
    @Id
    private String shortUrl;
    private String longUrl;
    boolean isActive;
}
