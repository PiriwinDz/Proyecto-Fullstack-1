package com.powerApp.pagos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long membresiaId;
    private BigDecimal monto;
    private String estado;
    private String metodoPago;
    private String referenciaPasarela;

    public void setMensaje(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMensaje'");
    }

}
