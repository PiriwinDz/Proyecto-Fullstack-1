package com.example.alertas.exception;

// runtime exception personalizada
public class AlertaNoEncontradaException extends RuntimeException {

    // constructor
    public AlertaNoEncontradaException(Long id) {

        // mensaje personalizado
        super("Alerta no encontrada con id: " + id);
    }
}
