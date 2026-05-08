package com.example.catalogo.dto;

@Data
@Builder
public class ErrorDTOEstandar {
    private LocalDateTime timestamp; // cuando paso 
    private int codigo;  //codigo http
    private String mensaje;
    private String ruta; // que url fallo 
    private Map<String, String> erroresValidacion; //
}
