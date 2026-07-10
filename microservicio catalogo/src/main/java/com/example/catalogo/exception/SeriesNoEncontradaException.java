package com.example.catalogo.exception;

public class SeriesNoEncontradaException extends RuntimeException {

    public SeriesNoEncontradaException(Long id) {
        super("Serie no encontrada con id: " + id);
    }

}
