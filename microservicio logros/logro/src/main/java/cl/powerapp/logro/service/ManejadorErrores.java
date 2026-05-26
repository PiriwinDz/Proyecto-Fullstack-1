package cl.powerapp.logro.service;

import cl.powerapp.logro.dto.ErrorDTO;
import cl.powerapp.logro.exception.LogroNoEncontradoException;
import cl.powerapp.logro.exception.LogroYaDesbloqueadoException;
import cl.powerapp.logro.exception.UsuarioNoEncontradoException;
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
 
    @ExceptionHandler(LogroNoEncontradoException.class)
    public ResponseEntity<ErrorDTO> manejarLogroNoEncontrado(
                LogroNoEncontradoException ex,
                HttpServletRequest request){

        Map<String, String> errores =
                new HashMap<>();

        errores.put(
                "logro",
                ex.getMessage());

        ErrorDTO errorDTO =
                new ErrorDTO(
                        LocalDateTime.now(),
                        404,
                        "Logro no encontrado",
                        errores,
                        request.getRequestURI()
                );

        return ResponseEntity.status(404).body(errorDTO);
    }

@ExceptionHandler(LogroYaDesbloqueadoException.class)

    public ResponseEntity<ErrorDTO> manejarLogroDuplicado(
            LogroYaDesbloqueadoException ex,
            HttpServletRequest request){

        Map<String, String> errores =
                new HashMap<>();

        errores.put(
                "logro",
                ex.getMessage());

        ErrorDTO errorDTO =
                new ErrorDTO(
                        LocalDateTime.now(),
                        409,
                        "Logro ya desbloqueado",
                        errores,
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(409)
                .body(errorDTO);
    }

@ExceptionHandler(UsuarioNoEncontradoException.class)

    public ResponseEntity<ErrorDTO> manejarUsuarioNoEncontrado(
            UsuarioNoEncontradoException ex,
            HttpServletRequest request){

        Map<String, String> errores =
                new HashMap<>();

        errores.put(
                "usuario",
                ex.getMessage());

        ErrorDTO errorDTO =
                new ErrorDTO(
                        LocalDateTime.now(),
                        404,
                        "Usuario no encontrado",
                        errores,
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(404)
                .body(errorDTO);
    }

}

