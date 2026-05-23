package com.example.autenticacion.exception;

// excepcion personalizada que se lanza cuando no se encuentra un usuario en la BD
// extiende RuntimeException para que Spring la maneje sin necesidad de try-catch
public class UsuarioNoEncontradoException extends RuntimeException {

    // constructor cuando se busca por id
    public UsuarioNoEncontradoException(Long id) {
        super("Usuario no encontrado con id: " + id);
    }

    // constructor cuando se quiere un mensaje personalizado (ej: login fallido)
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
