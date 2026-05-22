package com.powerApp.pagos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagoRequestDTO {

    private Long usuarioId;

    private Long membresiaId;

    private BigDecimal monto;

    private String metodoPago;

    private String referenciaPasarela;

}