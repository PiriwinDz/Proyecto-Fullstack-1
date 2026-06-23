
package com.example.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EjercicioDTO {
    @NotBlank(message= "El Nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message= "El Grupo muscular no puede estar vacio")
    private String GrupoMuscular;
    
    private String descripcion;
}
