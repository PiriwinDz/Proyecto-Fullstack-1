package com.example.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta para register y login
// contiene los datos del usuario y el token JWT generado
// no incluye el password ni el hash por seguridad
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // permite construirlo con AuthResponseDTO.builder().id(...).build()
public class AuthResponseDTO {

    private Long id;      // id del usuario en la BD
    private String nombre;
    private String correo;
    private String rol;   // ATLETA, TRABAJADOR o ADMINISTRADOR como texto
    private String token; // JWT generado, el frontend lo guarda y lo manda en cada peticion
}
