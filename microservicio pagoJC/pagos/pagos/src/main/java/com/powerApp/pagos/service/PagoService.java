package com.powerApp.pagos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    // GET: listar todos los pagos
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    // GET: buscar pago por ID
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // GET: buscar pagos por usuario
    public List<Pago> buscarPorUsuario(Long usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }

    // GET: buscar pagos por membresía
    public List<Pago> buscarPorMembresia(Long membresiaId) {
        return pagoRepository.findByMembresiaId(membresiaId);
    }

    // GET: buscar pagos por estado
    public List<Pago> buscarPorEstado(String estado) {
        return pagoRepository.findByEstado(EstadoPago.valueOf(estado.toUpperCase()));
    }

    // GET: buscar pagos por método de pago
    public List<Pago> buscarPorMetodoPago(String metodoPago) {
        return pagoRepository.findByMetodoPagoIgnoreCase(metodoPago);
    }

    // POST: guardar pago
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    // POST: crear pago desde DTO
    public PagoResponseDTO crearPago(PagoRequestDTO dto) {
        Pago pago = new Pago();

        pago.setUsuarioId(dto.getUsuarioId());
        pago.setMembresiaId(dto.getMembresiaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setReferenciaPasarela(dto.getReferenciaPasarela());
        pago.setEstado(EstadoPago.PENDIENTE);

        Pago pagoGuardado = pagoRepository.save(pago);

        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(pagoGuardado.getId());
        response.setMensaje("Pago creado correctamente");

        return response;
    }

    // DELETE: eliminar pago
    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    // PUT: actualizar pago
    public Pago actualizar(Long id, Pago nuevoPago) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setMonto(nuevoPago.getMonto());
        pago.setMetodoPago(nuevoPago.getMetodoPago());
        pago.setReferenciaPasarela(nuevoPago.getReferenciaPasarela());
        pago.setEstado(nuevoPago.getEstado());

        return pagoRepository.save(pago);
    }
}
