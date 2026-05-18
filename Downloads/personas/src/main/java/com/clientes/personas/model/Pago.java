package com.clientes.personas.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Pago {

    private Long id;
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fecha;
    private Integer personaId;

}
