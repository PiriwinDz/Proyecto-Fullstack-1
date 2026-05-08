package com.example.catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDTO {
    @NotBlank(message= "El Id del ejercicio no puede estar vacio")
    private Long EjercicioId;

    @Min(value = 0, message= "El peso debe ser positivo")
    private double peso;

    @Min(value = 1, message = "Minimo 1 repeticion")
    private int repeticion;
}
