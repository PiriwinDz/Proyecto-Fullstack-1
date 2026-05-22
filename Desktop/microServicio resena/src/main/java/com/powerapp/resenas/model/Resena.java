package com.powerapp.resenas.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "resenas")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(nullable = false)
    private Long usuarioId;

    @NotNull(message = "El ID del ejercicio es obligatorio")
    @Column(nullable = false)
    private Long ejercicioId;

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacion maxima es 5")
    @Column(nullable = false)
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacio")
    @Size(max = 300, message = "El comentario no puede superar 300 caracteres")
    @Column(nullable = false, length = 300)
    private String comentario;

    @Column(updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    public void prePersist() {

        creadoEn = LocalDateTime.now();
    }
}