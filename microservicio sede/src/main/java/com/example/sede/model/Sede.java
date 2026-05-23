package com.example.sede.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // le dice a JPA que esta clase es una tabla en la BD
@Table(name = "sedes") // nombre de la tabla en la BD
@Data // genera getters, setters y toString automaticamente (Lombok)
@NoArgsConstructor // constructor vacio requerido por JPA
@AllArgsConstructor // constructor con todos los campos
@Builder // permite crear objetos con Sede.builder().nombre(...).build()
public class Sede {

    @Id // clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremental en la BD
    private Long id;

    @NotBlank // no permite null ni texto vacio
    @Column(nullable = false, length = 100) // columna obligatoria, maximo 100 caracteres
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 200) // columna obligatoria, maximo 200 caracteres
    private String direccion;

    @Column(length = 100) // horario es opcional, ej: "Lun-Vie 06:00-23:00"
    private String horario;

    @NotNull
    @Positive // debe ser mayor a 0
    @Column(nullable = false) // cuantas personas caben en la sede
    private Integer capacidadMaxima;

    @Column(nullable = false) // cuantas personas hay actualmente en la sede
    private Integer ocupacionActual;

    @Column(nullable = false) // permite desactivar una sede sin borrarla de la BD
    private Boolean activo;

    private LocalDateTime creadoEn; // fecha y hora en que se registro la sede

    @PrePersist // se ejecuta automaticamente antes de guardar por primera vez en la BD
    public void prePersist() {
        if (this.ocupacionActual == null) { // si no se asigno ocupacion, empieza en 0
            this.ocupacionActual = 0;
        }
        if (this.activo == null) { // si no se asigno activo, la sede comienza activa
            this.activo = true;
        }
        if (this.creadoEn == null) { // si no se asigno fecha, la asigna ahora
            this.creadoEn = LocalDateTime.now();
        }
    }
}
