package com.clientes.personas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;    // Fecha y hora en que ocurrió el error
    private int status;                 // Código de estado HTTP (ej: 400, 404, 500)
    private String mensaje;             // Mensaje general del error
    private Map<String, String> errores;// Detalle de errores por campo (ej: "nombre" -> "no puede estar vacío")
    private String path;                // Ruta del endpoint donde ocurrió el error (ej: /agregar)

    // Constructor completo para crear el objeto con todos sus datos
    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje, Map<String, String> errores, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }
}
