package com.example.sede.exception;

import com.example.sede.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ManejadorErrores {

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0); 
        ErrorDTO errorDTO = new ErrorDTO(400, error.getDefaultMessage()); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO); 
    }

    
    @ExceptionHandler(SedeNoEncontradaException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(SedeNoEncontradaException ex) {
        ErrorDTO errorDTO = new ErrorDTO(404, ex.getMessage()); 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO); 
    }

    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarIllegal(IllegalArgumentException ex) {
        ErrorDTO errorDTO = new ErrorDTO(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO); 
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(Exception ex) {
        ErrorDTO errorDTO = new ErrorDTO(500, "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO); 
    }
}
