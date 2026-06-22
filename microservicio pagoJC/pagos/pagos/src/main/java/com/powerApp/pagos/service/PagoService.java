package com.powerApp.pagos.service;

import java.util.List;
import java.util.Optional;

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

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public List<Pago> buscarPorUsuario(Long usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pago> buscarPorMembresia(Long membresiaId) {
        return pagoRepository.findByMembresiaId(membresiaId);
    }

    public List<Pago> buscarPorEstado(String estado) {
        return pagoRepository.findByEstado(EstadoPago.valueOf(estado.toUpperCase()));
    }

    public List<Pago> buscarPorMetodoPago(String metodoPago) {
        return pagoRepository.findByMetodoPagoIgnoreCase(metodoPago);
    }

    public PagoResponseDTO crearPago(PagoRequestDTO dto) {
        Pago pago = Pago.builder()
                .usuarioId(dto.getUsuarioId())
                .membresiaId(dto.getMembresiaId())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .referenciaPasarela(dto.getReferenciaPasarela())
                .estado(EstadoPago.PENDIENTE)
                .build();

        Pago pagoGuardado = pagoRepository.save(pago);

        return PagoResponseDTO.builder()
                .id(pagoGuardado.getId())
                .usuarioId(pagoGuardado.getUsuarioId())
                .membresiaId(pagoGuardado.getMembresiaId())
                .monto(pagoGuardado.getMonto())
                .estado(pagoGuardado.getEstado().name())
                .metodoPago(pagoGuardado.getMetodoPago())
                .referenciaPasarela(pagoGuardado.getReferenciaPasarela())
                .mensaje("Pago creado correctamente")
                .build();
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    public Pago actualizar(Long id, Pago nuevoPago) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        Pago pagoActualizado = Pago.builder()
                .id(pago.getId())
                .usuarioId(pago.getUsuarioId())
                .membresiaId(pago.getMembresiaId())
                .monto(nuevoPago.getMonto())
                .estado(nuevoPago.getEstado())
                .metodoPago(nuevoPago.getMetodoPago())
                .referenciaPasarela(nuevoPago.getReferenciaPasarela())
                .creadoEn(pago.getCreadoEn())
                .procesadoEn(nuevoPago.getProcesadoEn())
                .build();

        return pagoRepository.save(pagoActualizado);
    }
}
