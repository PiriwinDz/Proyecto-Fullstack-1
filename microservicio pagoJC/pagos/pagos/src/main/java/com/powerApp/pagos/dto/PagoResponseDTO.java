package com.powerApp.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long membresiaId;
    private BigDecimal monto;
    private String estado;
    private String metodoPago;
    private String referenciaPasarela;
    private String mensaje;
}
