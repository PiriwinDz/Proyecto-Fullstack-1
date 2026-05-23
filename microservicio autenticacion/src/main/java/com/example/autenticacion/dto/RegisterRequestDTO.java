package com.example.autenticacion.dto;

import com.example.autenticacion.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO = Data Transfer Object
// solo transporta los datos que llegan del frontend al registrarse
// no expone la entidad Usuario directamente
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre es obligatorio") // si llega vacio, devuelve ese mensaje de error
    private String nombre;

    @Email(message = "El correo no tiene un formato valido") // valida formato correo@dominio.com
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres") // minimo 8 caracteres
    private String password; // llega en texto plano, se encripta en el service

    @NotNull(message = "El rol es obligatorio") // debe ser ATLETA, TRABAJADOR o ADMINISTRADOR
    private RolUsuario rol;
}
