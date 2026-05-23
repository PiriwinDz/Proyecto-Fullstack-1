package com.example.sede.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO que transporta los datos que llegan del frontend al crear o editar una sede
// no expone la entidad Sede directamente para proteger campos internos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeRequestDTO {

    @NotBlank(message = "El nombre de la sede es obligatorio")
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    private String horario; // campo opcional, puede llegar null

    @NotNull(message = "La capacidad maxima es obligatoria")
    @Positive(message = "La capacidad maxima debe ser mayor a cero")
    private Integer capacidadMaxima;
}
