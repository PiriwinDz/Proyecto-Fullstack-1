package com.example.sede.exception;

// excepcion personalizada que se lanza cuando no se encuentra una sede en la BD
// extiende RuntimeException para que Spring la maneje sin necesidad de try-catch obligatorio
public class SedeNoEncontradaException extends RuntimeException {

    // constructor cuando se busca por id
    public SedeNoEncontradaException(Long id) {
        super("Sede no encontrada con id: " + id);
    }

    // constructor cuando se quiere un mensaje personalizado
    public SedeNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
