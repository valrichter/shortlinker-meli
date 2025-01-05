package com.meli.shortlinker.repository;

import com.meli.shortlinker.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {

}