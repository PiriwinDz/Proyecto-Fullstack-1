package com.example.sede.exception;



public class SedeNoEncontradaException extends RuntimeException {

    
    public SedeNoEncontradaException(Long id) {
        super("Sede no encontrada con id: " + id);
    }

    
    public SedeNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
