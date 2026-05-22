package com.powerapp.resenas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data

public class ResenaRequestDTO {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ejercicioId es obligatorio")
    private Long ejercicioId;

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacion maxima es 5")
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacio")
    private String comentario;

}