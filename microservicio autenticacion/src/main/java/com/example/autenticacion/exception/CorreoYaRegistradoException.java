package com.example.autenticacion.exception;

public class CorreoYaRegistradoException extends RuntimeException {

    public CorreoYaRegistradoException(String correo) {
        super("Ya existe una cuenta registrada con el correo: " + correo);
    }

}