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

@Entity 
@Table(name = "sedes") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Sede {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank 
    @Column(nullable = false, length = 100) 
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 200) 
    private String direccion;

    @Column(length = 100) 
    private String horario;

    @NotNull
    @Positive 
    @Column(nullable = false) 
    private Integer capacidadMaxima;

    @Column(nullable = false) 
    private Integer ocupacionActual;

    @Column(nullable = false) 
    private Boolean activo;

    private LocalDateTime creadoEn; 

    @PrePersist 
    public void prePersist() {
        if (this.ocupacionActual == null) { 
            this.ocupacionActual = 0;
        }
        if (this.activo == null) { 
            this.activo = true;
        }
        if (this.creadoEn == null) { 
            this.creadoEn = LocalDateTime.now();
        }
    }
}
