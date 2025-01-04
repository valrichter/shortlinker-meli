package com.meli.shortlinker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
            title = "Shortlinker REST API",
            version = "v1.0.0",
            description = "Shortlinker API for Mercado Libre (MELI)"
    )
)
public class OpenAPIConfig {
}
