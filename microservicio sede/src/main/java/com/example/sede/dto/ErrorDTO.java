package com.example.sede.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO estandar para devolver errores al cliente
// se usa en el ManejadorErrores para todas las respuestas de error
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private int codigo;     // codigo HTTP: 400 bad request, 404 not found, 500 error servidor
    private String mensaje; // descripcion del error en texto legible
}
