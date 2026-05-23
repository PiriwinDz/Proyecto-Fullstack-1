package com.example.alertas.model;

// importaciones jpa
import jakarta.persistence.*;

// importaciones lombok
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// importacion fecha
import java.time.LocalDateTime;

// indica que esta clase es una entidad de base de datos
@Entity

// nombre de la tabla
@Table(name = "alertas")

// genera getters setters toString equals hashCode
@Data

// constructor vacio
@NoArgsConstructor

// constructor con todos los campos
@AllArgsConstructor

// patron builder
@Builder
public class Alerta {

    // clave primaria
    @Id

    // id autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // titulo de la alerta
    @Column(nullable = false)
    private String titulo;

    // descripcion de la alerta
    @Column(nullable = false)
    private String mensaje;

    // tipo de alerta
    @Column(nullable = false)
    private String tipo;

    // estado activo o inactivo
    @Column(nullable = false)
    private Boolean activa;

    // fecha de creacion
    private LocalDateTime creadaEn;

    // se ejecuta antes de guardar en la bd
    @PrePersist
    public void prePersist() {

        // si activa viene null se asigna true
        if (this.activa == null) {
            this.activa = true;
        }

        // asigna fecha actual
        if (this.creadaEn == null) {
            this.creadaEn = LocalDateTime.now();
        }
    }
}
