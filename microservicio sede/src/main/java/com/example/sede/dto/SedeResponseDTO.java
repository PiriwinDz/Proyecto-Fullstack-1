package com.example.sede.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO de respuesta que se devuelve al cliente con los datos de la sede
// incluye campos calculados como porcentajeOcupacion que no estan en la BD
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // permite construirlo con SedeResponseDTO.builder().nombre(...).build()
public class SedeResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String horario;
    private Integer capacidadMaxima;   // cuantas personas caben en total
    private Integer ocupacionActual;   // cuantas personas hay en este momento
    private Integer porcentajeOcupacion; // ocupacionActual / capacidadMaxima * 100
    private Boolean activo;
    private LocalDateTime creadoEn;
}
