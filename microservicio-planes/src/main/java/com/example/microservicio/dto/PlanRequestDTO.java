package com.example.microservicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PlanRequestDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @PositiveOrZero(message = "El coste debe ser mayor o igual a cero")
    private double coste;
    
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
}