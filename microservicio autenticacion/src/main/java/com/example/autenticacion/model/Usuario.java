package com.example.autenticacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity 
@Table(name = "usuarios") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @NotBlank 
    @Column(nullable = false, length = 100) 
    private String nombre;
    
    @Email 
    @NotBlank 
    @Column(unique = true, nullable = false) 
    private String correo;
    
    @NotBlank 
    @Column(nullable = false) 
    private String password; 
    
    @Enumerated(EnumType.STRING) 
    @NotNull 
    @Column(nullable = false) 
    private RolUsuario rol; 

    @Column(nullable = false) 
    private Boolean activo; 

    private LocalDateTime creadoEn; 

    @PrePersist 
    public void prePersist() {
        if (this.creadoEn == null) { 
            this.creadoEn = LocalDateTime.now();
        }
        if (this.activo == null) { 
            this.activo = true;
        }
    }
}
