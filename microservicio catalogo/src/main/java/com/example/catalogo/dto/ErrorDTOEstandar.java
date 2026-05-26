package com.example.catalogo.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTOEstandar {
    private LocalDateTime timestamp; 
    private int codigo;  
    private String mensaje;
    private String ruta;  
    private Map<String, String> erroresValidacion; 
}
