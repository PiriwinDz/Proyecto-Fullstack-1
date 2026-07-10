package com.example.catalogo.exception;

public class EjercicioNoEncontradoException extends RuntimeException {

    public EjercicioNoEncontradoException(Long id) {
        super("Ejercicio no encontrado con id: " + id);
    }

}
