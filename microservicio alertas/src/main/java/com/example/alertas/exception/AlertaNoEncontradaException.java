package com.example.alertas.exception;


public class AlertaNoEncontradaException extends RuntimeException {

    
    public AlertaNoEncontradaException(Long id) {

        
        super("Alerta no encontrada con id: " + id);
    }
}
