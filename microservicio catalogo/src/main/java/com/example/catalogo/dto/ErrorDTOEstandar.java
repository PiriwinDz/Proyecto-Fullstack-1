package com.example.catalogo.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTOEstandar {
    private LocalDateTime timestamp; // cuando paso 
    private int codigo;  //codigo http
    private String mensaje;
    private String ruta; // que url fallo 
    private Map<String, String> erroresValidacion; //
}
