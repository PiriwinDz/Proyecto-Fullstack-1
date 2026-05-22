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

    // GET: listar todos los pagos
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    // GET: buscar por metodo de pago
    public List<Pago> buscarPorMetodoDePago(String metodoPago) {
        return pagoRepository.findByMetodoPagoIgnoreCase(metodoPago);
    }

    // GET: buscar por ID
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // GET: buscar por usuarioId
    public List<Pago> buscarPorUsuario(Long usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }

    // GET: buscar por estado
    public List<Pago> buscarPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado);
    }

    // GET: pagos pendientes
    public List<Pago> pagosPendientes() {
        return pagoRepository.findByEstado(EstadoPago.PENDIENTE);
    }

    // GET: buscar por membresiaId
    public List<Pago> buscarPorMembresia(Long membresiaId) {
        return pagoRepository.findByMembresiaId(membresiaId);
    }

    // POST: guardar pago
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    // POST: procesar pago
    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {

        Pago pago = new Pago();

        pago.setUsuarioId(dto.getUsuarioId());
        pago.setMembresiaId(dto.getMembresiaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setReferenciaPasarela(dto.getReferenciaPasarela());

        Pago pagoGuardado = pagoRepository.save(pago);

        PagoResponseDTO response = new PagoResponseDTO();

        response.setId(pagoGuardado.getId());
        response.setEstado(pagoGuardado.getEstado());
        response.setMensaje("Pago procesado correctamente");

        return response;
    }

    // DELETE: eliminar pago por ID
    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    // PUT: actualizar estado del pago
    public Pago actualizarEstado(Long id, EstadoPago nuevoEstado) {

        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setEstado(nuevoEstado);

        return pagoRepository.save(pago);
    }

}