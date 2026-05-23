package com.example.alertas.dto;

// lombok
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// fecha
import java.time.LocalDateTime;

// dto de respuesta para devolver alertas
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaResponseDTO {

    // id de la alerta
    private Long id;

    // titulo
    private String titulo;

    // mensaje
    private String mensaje;

    // tipo
    private String tipo;

    // estado activo
    private Boolean activa;

    // fecha creacion
    private LocalDateTime creadaEn;
}
