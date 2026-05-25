package com.example.alertas.exception;


import com.example.alertas.dto.ErrorDTO;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ManejadorErrores {

    
    @ExceptionHandler(AlertaNoEncontradaException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(AlertaNoEncontradaException ex) {

        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(404, ex.getMessage()));
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(Exception ex) {

        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(500, "Error interno del servidor"));
    }
}
