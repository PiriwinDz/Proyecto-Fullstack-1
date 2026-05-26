package com.example.sede.exception;

import com.example.sede.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice intercepta todas las excepciones lanzadas en los controllers
// evita tener try-catch en cada metodo del controller
@RestControllerAdvice
public class ManejadorErrores {

    // se ejecuta cuando falla una validacion @Valid en el controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0); // toma el primer campo que fallo
        ErrorDTO errorDTO = new ErrorDTO(400, error.getDefaultMessage()); // usa el mensaje definido en el DTO
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO); // devuelve 400
    }

    // se ejecuta cuando se lanza SedeNoEncontradaException en el service
    @ExceptionHandler(SedeNoEncontradaException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(SedeNoEncontradaException ex) {
        ErrorDTO errorDTO = new ErrorDTO(404, ex.getMessage()); // mensaje del constructor de la excepcion
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO); // devuelve 404
    }

    // se ejecuta cuando se lanza IllegalArgumentException (ej: sede llena, sede ya desactivada)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarIllegal(IllegalArgumentException ex) {
        ErrorDTO errorDTO = new ErrorDTO(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO); // devuelve 400
    }

    // captura cualquier otra excepcion no controlada para evitar que el servidor muestre errores internos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(Exception ex) {
        ErrorDTO errorDTO = new ErrorDTO(500, "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO); // devuelve 500
    }
}
