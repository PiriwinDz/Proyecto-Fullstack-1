package com.example.microservicio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanResponseDTO {
    private Long id;
    private String nombre;
    private double coste;
    private String descripcion;
}