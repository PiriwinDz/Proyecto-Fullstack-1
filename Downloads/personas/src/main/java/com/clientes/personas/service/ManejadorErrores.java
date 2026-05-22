package com.clientes.personas.service;

import com.clientes.personas.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException; // para validar contra la bd
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // para validar con jakarta
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Esta clase captura errores de toda la aplicación de forma global
// Evita tener try-catch en cada controller
@RestControllerAdvice
public class ManejadorErrores {

    // Manejo de errores de validación (cuando falla @Valid en un request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Indica que este método se ejecuta automáticamente cuando ocurre una excepción de validación
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
            MethodArgumentNotValidException ex, // Contiene el detalle de los errores de validación
            HttpServletRequest request) {       // Permite obtener información del request (ej: la URL)

        // Mapa donde se almacenarán los errores por campo (ej: "nombre" -> "no puede estar vacío")
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> { 
            // Se recorren todos los errores de validación detectados por Spring
            errores.put(error.getField(), error.getDefaultMessage()); 
            // Se guarda el nombre del campo y su mensaje de error en el mapa
        });

        // Se crea un objeto ErrorDTO con la información del error
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),                // Fecha y hora en que ocurrió el error
            400,                                // Código HTTP (400 = Bad Request)
            "Error de validación",              // Mensaje general del error
            errores,                            // Detalle de errores por campo
            request.getRequestURI()             // URL del endpoint donde ocurrió el error
        );

        // Se construye la respuesta HTTP:
        // - badRequest() -> establece el estado HTTP 400
        // - body(errorDTO) -> envía el objeto ErrorDTO como respuesta en formato JSON
        return ResponseEntity.badRequest().body(errorDTO);
    }

    // Manejo de errores de bases de datos
    @ExceptionHandler (DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBasesDatos(
        DataIntegrityViolationException ex,
        HttpServletRequest request) {
            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "El email ya está registrado",
                null,
                request.getRequestURI()
            );
            return ResponseEntity.badRequest().body(errorDTO);
        }
}


