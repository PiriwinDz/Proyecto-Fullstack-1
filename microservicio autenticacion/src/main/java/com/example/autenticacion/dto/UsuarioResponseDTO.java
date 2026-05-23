package com.example.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO de respuesta para cuando se consulta un usuario
// no incluye el password por seguridad
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String rol;            // rol del usuario en texto
    private Boolean activo;        // si la cuenta esta activa o desactivada
    private LocalDateTime creadoEn; // fecha de creacion del usuario
}
