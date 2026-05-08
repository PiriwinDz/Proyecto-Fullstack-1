package com.example.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ejercicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Ejercicio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTIFY)
    private int Id;

    @NotNull(message= "El nombre es obligatorio")
    @Column(nullable = false, unique = true)
    private String Nombre;

    @NotBlank(message= "el grupo muscular es obligatorio")
    @Column(nullable = false, unique = true)
    private String GrupoMuscular;

    private String descripcion;

}
