package com.example.alertas.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaResponseDTO {

    
    private Long id;

    
    private String titulo;

    
    private String mensaje;

    
    private String tipo;

    
    private Boolean activa;

    
    private LocalDateTime creadaEn;
}
