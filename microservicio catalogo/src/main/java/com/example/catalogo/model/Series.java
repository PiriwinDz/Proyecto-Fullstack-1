package com.example.catalogo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Series {
    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message= "El id del ejercicio es obligatorio")
    private Long EjercicioId;
    private double peso;
    private int repeticiones;
    private double RmEstimado;

    @Column(name= "fecha_hora")
    private LocalDateTime fechaHora;
     
    @PrePersist
    @PreUpdate
    public void calcularRm(){
        if(this.fechaHora == null){
            this.fechaHora = LocalDateTime.now();
        }
        if(this.repeticiones > 0){
            this.RmEstimado = this.peso * (1 + (double)this.repeticiones / 30);
        } else{
            this.RmEstimado = this.peso;
        }
    }
}
