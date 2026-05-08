package com.example.catalogo.exception;

import java.time.LocalDateTime;

@RestControllerAdvice // lo define como el manejador global 
public class GlobalExceptionHandler {

    // este unico metodo atrapa especificamente los fallos de las anotacione @valid ej notnull o blank o size
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTOEstandar> handleValidationException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {

            //captura cada campo con su error 
            Map<String, String> errors = new HashMap<>();

            //sacamos los errores que vienen de Bean validation
            ex.getBindingResult().getFieldErrors().forEach(error-> 
            errors.put(error.getField(), error.getDefaultMessage()));

            //el DTO de error estandar 
            ErrorDTOEstandar errorDTO = ErrorDTOEstandar.builder()
            .timestap(LocalDateTime.now())
            .codigo(HttpStatus.BAD_REQUEST.value())
            .mensaje("Error en la validacion de los campos enviados")
            .ruta(request.getRequestURI())// indica que url fallo 
            .erroresValidacion/(errors)
            .build();
            // se retorna el ResponseEntity con el objeto y el codigo HTTP
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

        }
    
}
