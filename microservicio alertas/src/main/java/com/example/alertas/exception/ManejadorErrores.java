package com.example.alertas.exception;

// dto error
import com.example.alertas.dto.ErrorDTO;

// response entity
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// exception handler
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// manejo global errores
@RestControllerAdvice
public class ManejadorErrores {

    // maneja alerta no encontrada
    @ExceptionHandler(AlertaNoEncontradaException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(AlertaNoEncontradaException ex) {

        // retorna 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(404, ex.getMessage()));
    }

    // maneja errores generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(Exception ex) {

        // retorna 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(500, "Error interno del servidor"));
    }
}
