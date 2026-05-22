package com.clientes.personas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore; // para ocultar el password en respuestas 
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message =  "El nombre no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message =  "El apellido no puede estar vacio")
    private String apellido;

    @Email(message =  "Debe ser un correo valido")
    @NotBlank(message =  "El email no puede estar vacio")
    @Column(unique = true)
    private String email;

    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Column(nullable = false)
    private String password;

    @ElementCollection
    private List<Integer> pagosIds;

    

}
