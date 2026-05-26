package com.example.alertas.dto;

// lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto para errores
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    // codigo http
    private int codigo;

    // mensaje error
    private String mensaje;
}
