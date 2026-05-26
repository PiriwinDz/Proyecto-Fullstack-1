package com.example.autenticacion.dto;

import com.example.autenticacion.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre es obligatorio") 
    private String nombre;

    @Email(message = "El correo no tiene un formato valido") 
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres") 
    private String password; 

    @NotNull(message = "El rol es obligatorio") 
    private RolUsuario rol;
}
