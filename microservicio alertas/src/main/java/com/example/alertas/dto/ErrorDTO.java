package com.example.alertas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    
    private int codigo;

    
    private String mensaje;
}
