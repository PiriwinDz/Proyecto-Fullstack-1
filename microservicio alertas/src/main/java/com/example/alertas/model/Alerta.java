package com.example.alertas.model;


import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Entity


@Table(name = "alertas")


@Data


@NoArgsConstructor


@AllArgsConstructor


@Builder
public class Alerta {

    
    @Id

    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false)
    private String titulo;

    
    @Column(nullable = false)
    private String mensaje;

    
    @Column(nullable = false)
    private String tipo;

    
    @Column(nullable = false)
    private Boolean activa;

    
    private LocalDateTime creadaEn;

    
    @PrePersist
    public void prePersist() {

        
        if (this.activa == null) {
            this.activa = true;
        }

        
        if (this.creadaEn == null) {
            this.creadaEn = LocalDateTime.now();
        }
    }
}
