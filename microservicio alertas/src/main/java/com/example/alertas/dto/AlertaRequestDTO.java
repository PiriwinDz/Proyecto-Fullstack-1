package com.example.alertas.dto;


import jakarta.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRequestDTO {

    
    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;
}
