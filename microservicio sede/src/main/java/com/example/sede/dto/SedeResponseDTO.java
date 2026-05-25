package com.example.sede.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class SedeResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String horario;
    private Integer capacidadMaxima;   
    private Integer ocupacionActual;   
    private Integer porcentajeOcupacion; 
    private Boolean activo;
    private LocalDateTime creadoEn;
}
