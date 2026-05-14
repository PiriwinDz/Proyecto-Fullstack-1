package com.powerApp.pagos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        throw new UnsupportedOperationException("Implementar procesamiento con Transbank/Stripe");
    }

    public List<Pago> pagosPendientes() {
        return pagoRepository.findByEstado(EstadoPago.PENDIENTE);
    }

}