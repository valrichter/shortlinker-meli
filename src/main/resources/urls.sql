-- Eliminar tabla si existe
DROP TABLE IF EXISTS "urls";

-- Crear tabla "urls"
CREATE TABLE IF NOT EXISTS "urls" (
  "short_url" varchar(30) UNIQUE PRIMARY KEY,
  "slug" varchar(8) UNIQUE,
  "user_id" uuid,
  "long_url_protocol" varchar(10) NOT NULL,
  "long_url_domain" varchar(90) NOT NULL,
  "long_url_path" varchar(400),
  "is_active" boolean NOT NULL DEFAULT true,
  "created_at" timestamptz NOT NULL DEFAULT (now()),
  "stats_count" integer NOT NULL DEFAULT 0
);

-- Crear indice sobre el primer caracter del campo "slug"
CREATE INDEX idx_slug_first_char ON urls ((substring(slug from 1 for 1)));

-- Insertar algunos datos de ejemplo en la tabla "urls"
INSERT INTO "urls" ("short_url", "slug", "user_id", "long_url_protocol", "long_url_domain", "long_url_path")
VALUES
  ('meli.ly/25years', '25years', null, 'https', 'www.mercadolibre.com.ar', null),
  ('meli.ly/rfvt9876', 'rfvt9876', 'f4a7e36c-8f27-43de-91c5-67ab8a61d215', 'https', 'www.linkedin.com', 'in/valrichter'),
  ('meli.ly/uvwx3456', 'uvwx3456', 'f4a7e36c-8f27-43de-91c5-67ab8a61d215', 'http', 'www.github.com', '/valrichter'),
  ('meli.ly/abcd1234', 'abcd1234', null, 'https', 'www.example.com', 'products/item123'),
  ('meli.ly/wxyz5678', 'wxyz5678', null, 'http', 'www.testsite.com', 'categories/electronics'),
  ('meli.ly/qwer4321', 'qwer4321', 'd290f1ee-6c54-4b01-90e6-d701748f0851', 'https', 'www.google.com', 'search?q=short+url'),
  ('meli.ly/asdf5678', 'asdf5678', null, 'https', 'www.youtube.com', 'watch?v=dQw4w9WgXcQ'),
  ('meli.ly/zxcv0987', 'zxcv0987', '123e4567-e89b-12d3-a456-426614174000', 'https', 'www.wikipedia.org', null),
  ('meli.ly/mnop1234', 'mnop1234', 'e4d909c2-90fa-4c34-99f1-cdfb31d3cbf4', 'http', 'www.example.org', 'resources/documentation'),
  ('meli.ly/ijkl8765', 'ijkl8765', null, 'https', 'www.openai.com', 'research/gpt'),
  ('meli.ly/ytre9876', 'ytre9876', 'c2c92f87-560b-41d1-91f3-58e0a1d5409a', 'https', 'www.medium.com', 'posts/short-url-tips'),
  ('meli.ly/poiu1234', 'poiu1234', 'd9b3bcf7-b32f-40e4-889e-c3e76383062b', 'https', 'www.stackoverflow.com', 'questions/12345'),
  ('meli.ly/nbvc3456', 'nbvc3456', null, 'https', 'www.reddit.com', 'r/programming/comments/abc123'),
  ('meli.ly/hgfz8765', 'hgfz8765', 'c2c92f87-560b-4d19-91f3-58e0a1d5409a', 'http', 'www.amazon.com', 'gp/product/B08XYZ1234'),
  ('meli.ly/edcv1234', 'edcv1234', null, 'https', 'www.twitter.com', 'user/status/123456789'),
  ('meli.ly/tgbn8765', 'tgbn8765', null, 'http', 'www.netflix.com', 'title/80057281');
