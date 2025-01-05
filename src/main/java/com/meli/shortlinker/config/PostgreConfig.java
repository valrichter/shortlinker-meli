package com.meli.shortlinker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class PostgreConfig implements CommandLineRunner {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public void run(String... args) throws Exception {
            // Leer y ejecutar el script SQL desde un archivo
            Resource resource = new ClassPathResource("urls.sql");
            String sql = Files.readString(resource.getFile().toPath());
            jdbcTemplate.execute(sql);
            System.out.println("Initial data loaded into Postgres");
        }
    }

