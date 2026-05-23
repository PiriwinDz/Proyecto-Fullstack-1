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

@Entity // le dice a JPA que esta clase es una tabla en la BD
@Table(name = "usuarios") // nombre de la tabla en la BD
@Data // genera getters, setters, toString automaticamente (Lombok)
@NoArgsConstructor // genera constructor vacio requerido por JPA
@AllArgsConstructor // genera constructor con todos los campos
@Builder // permite crear objetos con el patron builder: Usuario.builder().nombre(...).build()
public class Usuario {

    @Id // indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremental en la BD
    private Long id;

    @NotBlank // no permite null ni vacio
    @Column(nullable = false, length = 100) // columna obligatoria, maximo 100 caracteres
    private String nombre;

    @Email // valida que el formato sea correo@dominio.com
    @NotBlank // no permite null ni vacio
    @Column(unique = true, nullable = false) // no puede repetirse en la BD
    private String correo;

    @NotBlank // no permite null ni vacio
    @Column(nullable = false) // columna obligatoria en la BD
    private String password; // se guarda encriptado con BCrypt, nunca en texto plano

    @Enumerated(EnumType.STRING) // guarda el nombre del enum como texto (ATLETA, TRABAJADOR...)
    @NotNull // no permite null
    @Column(nullable = false) // columna obligatoria en la BD
    private RolUsuario rol; // define los permisos del usuario en el sistema

    @Column(nullable = false) // columna obligatoria en la BD
    private Boolean activo; // permite desactivar un usuario sin borrarlo de la BD

    private LocalDateTime creadoEn; // fecha y hora exacta en que se creo el usuario

    @PrePersist // se ejecuta automaticamente antes de guardar en la BD
    public void prePersist() {
        if (this.creadoEn == null) { // si no se asigno fecha, la asigna ahora
            this.creadoEn = LocalDateTime.now();
        }
        if (this.activo == null) { // si no se asigno activo, lo pone en true por defecto
            this.activo = true;
        }
    }
}
