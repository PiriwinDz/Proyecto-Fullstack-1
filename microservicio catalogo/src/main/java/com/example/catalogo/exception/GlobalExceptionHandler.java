package com.example.catalogo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.catalogo.dto.ErrorDTOEstandar;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTOEstandar> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
                .timestamp(LocalDateTime.now())
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error en la validación de los campos enviados")
                .ruta(request.getRequestURI())
                .erroresValidacion(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(EjercicioNoEncontradoException.class)
    public ResponseEntity<ErrorDTOEstandar> handleEjercicioNoEncontrado(
            EjercicioNoEncontradoException ex,
            HttpServletRequest request) {

        ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
                .timestamp(LocalDateTime.now())
                .codigo(HttpStatus.NOT_FOUND.value())
                .mensaje(ex.getMessage())
                .ruta(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(SeriesNoEncontradaException.class)
    public ResponseEntity<ErrorDTOEstandar> handleSerieNoEncontrada(
            SeriesNoEncontradaException ex,
            HttpServletRequest request) {

        ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
                .timestamp(LocalDateTime.now())
                .codigo(HttpStatus.NOT_FOUND.value())
                .mensaje(ex.getMessage())
                .ruta(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTOEstandar> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
                .timestamp(LocalDateTime.now())
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .mensaje("Error interno del servidor")
                .ruta(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

}