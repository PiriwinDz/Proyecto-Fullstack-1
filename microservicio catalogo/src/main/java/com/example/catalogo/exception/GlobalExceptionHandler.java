package com.example.catalogo.exception;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.example.catalogo.dto.ErrorDTOEstandar;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTOEstandar> handleValidationException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {

            
            Map<String, String> errors = new HashMap<>();

            
            ex.getBindingResult().getFieldErrors().forEach(error-> 
            errors.put(error.getField(), error.getDefaultMessage()));

            
            ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
            .timestamp(LocalDateTime.now())
            .codigo(HttpStatus.BAD_REQUEST.value())
            .mensaje("Error en la validacion de los campos enviados")
            .ruta(request.getRequestURI())
            .erroresValidacion(errors)
            .build();
            
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

        }
    
}
