package cl.powerapp.logro.service;

import cl.powerapp.logro.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
        MethodArgumentNotValidException ex,
        HttpServletRequest request){

            Map<String, String> errores = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            400,
            "Error de validacion",
            errores,
            request.getRequestURI()
         );

         return ResponseEntity.badRequest().body(errorDTO);

    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<ErrorDTO> manejarErrorQuinientos(
        Exception ex, HttpServletRequest request){

                Map<String, String> errores = new HashMap<>();

                errores.put("error", ex.getMessage());

                ErrorDTO errorDTO = new ErrorDTO(
                    LocalDateTime.now(),
                            500,
                            "Error interno del servidor",
                            errores, request.getRequestURI() 
                        );
        return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR) 
        .body(errorDTO);
    }
}

