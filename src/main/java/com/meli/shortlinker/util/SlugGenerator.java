package com.meli.shortlinker.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SlugGenerator {
    // Nil UUID: 00000000-0000-0000-0000-000000000000
    private static final UUID NIL_UUID = new UUID(0L, 0L);

    // Método para generar una URL corta única de 8 caracteres
    public static String generate(String longUrl, UUID userId, String createdAt) throws NoSuchAlgorithmException {
        // Paso 1: Utilizar NIL_UUID si userId es null
        UUID effectiveUserId = (userId != null) ? userId : NIL_UUID;

        // Paso 2: Concatenar la URL larga, el ID de usuario (o NIL_UUID) y la fecha de creación
        String input = longUrl + effectiveUserId.toString() + createdAt;

        // Paso 3: Generar el hash SHA-256 de la cadena concatenada
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());

        // Paso 4: Codificar el hash en Base62 y obtener los primeros 8 caracteres
        String base62Encoded = encodeBase62(hashBytes);
        String slug = base62Encoded.substring(0, 8);

        // Paso 5: Verificar si el slug ya existe y manejar colisiones
        int counter = 0;
        while (slugExists(slug)) {
            counter++;
            slug = base62Encoded.substring(0, 7) + counter;
        }

        // Paso 6: Devolver el slug generado
        return slug;
    }

    // Método para codificar un arreglo de bytes en Base62
    private static String encodeBase62(byte[] bytes) {
        StringBuilder base62 = new StringBuilder();
        String base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Recorrer cada byte del array
        for (byte b : bytes) {
            // Asegurarse de que el valor esté en el rango de 0 a 61
            int index = b & 0x3F; // Esto asegura que el índice esté entre 0 y 63
            base62.append(base62Chars.charAt(index % 62)); // Usar módulo 62 para mantener el rango adecuado
        }
        return base62.toString();
    }

    // Método simulado para verificar si una URL corta ya existe
    private static boolean slugExists(String shortUrl) {
        // Lógica para verificar si la URL corta ya existe en la base de datos
        // Por ejemplo, consultar una base de datos o un caché
        return false; // Simulación: siempre retorna false
    }

}
