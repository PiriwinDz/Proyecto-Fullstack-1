package com.example.alertas.dto;

// validaciones
import jakarta.validation.constraints.NotBlank;

// lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto que recibe datos desde el frontend
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRequestDTO {

    // titulo obligatorio
    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    // mensaje obligatorio
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    // tipo obligatorio
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;
}
