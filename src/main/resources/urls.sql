-- Eliminar tabla si existe
DROP TABLE IF EXISTS "urls";

-- Crear tabla "urls"
CREATE TABLE IF NOT EXISTS "urls" (
  "short_url" varchar(30) UNIQUE PRIMARY KEY,
  "slug" varchar(8) UNIQUE,
  "user_id" uuid,
  "long_url_protocol" varchar(10) NOT NULL,
  "long_url_domain" varchar(90) NOT NULL,
  "long_url_path" varchar(400) NOT NULL,
  "is_active" boolean NOT NULL DEFAULT true,
  "created_at" timestamptz NOT NULL DEFAULT (now()),
  "stats_count" integer NOT NULL DEFAULT 0
);

-- Crear indice sobre el primer caracter del campo "slug"
CREATE INDEX idx_slug_first_char ON urls ((substring(slug from 1 for 1)));

-- Insertar algunos datos de ejemplo en la tabla "urls"
INSERT INTO "urls" ("short_url", "slug", "user_id", "long_url_protocol", "long_url_domain", "long_url_path")
VALUES
  ('https://short/url', 'abcd', '123e4567-e89b-12d3-a456-426614174000', 'https', 'long', '/url'),
  ('https://short.ly/efgh', 'efgh', '123e4567-e89b-12d3-a456-426614174000', 'https', 'paginaweb.ar', '/producto?true=1'),
  ('https://short.ly/ijkl', 'ijkl', '123e4567-e89b-12d3-a456-426614174000', 'https', 'google.com', '/search?q=shortly'),
  ('https://short.ly/mnop', 'mnop', '123e4567-e89b-12d3-a456-426614174000', 'https', 'facebook.com', '/profile=shortly'),
  ('https://short.ly/qrst', 'qrst', '123e4567-e89b-12d3-a456-426614174000', 'https', 'twitter.com', '/user=shortly'),
  ('https://short.ly/uvwx', 'uvwx', '123e4567-e89b-12d3-a456-426614174000', 'https', 'instagram.com', '/user=shortly'),
  ('https://short.ly/yzab', 'yzab', '123e4567-e89b-12d3-a456-426614174000', 'https', 'linkedin.com', '/profile=shortly'),
  ('https://short.ly/cdef', 'cdef', '123e4567-e89b-12d3-a456-426614174000', 'https', 'youtube.com', '/channel=shortly'),
  ('https://short.ly/ghij', 'ghij', '123e4567-e89b-12d3-a456-426614174000', 'https', 'twitch.com', '/user=shortly'),
  ('https://short.ly/klmn', 'klmn', '123e4567-e89b-12d3-a456-426614174000', 'https', 'tiktok.com', '/user=shortly');