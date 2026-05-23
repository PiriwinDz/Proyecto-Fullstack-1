package com.example.autenticacion.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service // lo registra como componente de Spring para poder inyectarlo con @RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}") // lee el valor desde application.properties
    private String secret;

    @Value("${jwt.expiration}") // lee el tiempo de expiracion en milisegundos (86400000 = 24 horas)
    private long expiration;

    // genera un token JWT con los datos del usuario
    public String generarToken(Long usuarioId, String correo, String rol) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); // convierte el secret en una clave de firma

        return Jwts.builder()
                .subject(correo)                  // identifica al dueno del token
                .claim("usuarioId", usuarioId)    // agrega el id como dato extra
                .claim("rol", rol)                // agrega el rol para control de accesos
                .issuedAt(new Date())             // fecha de creacion del token
                .expiration(new Date(System.currentTimeMillis() + expiration)) // fecha de vencimiento
                .signWith(key)                    // firma el token para que no pueda ser modificado
                .compact();                       // lo convierte en el string final del JWT
    }

    // verifica si un token es valido y no esta vencido
    public boolean validarToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token); // si falla lanza excepcion
            return true; // token valido
        } catch (Exception e) {
            return false; // token invalido o vencido
        }
    }

    // extrae el correo guardado en el subject del token
    public String obtenerCorreo(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // retorna el correo guardado en el token
    }
}
