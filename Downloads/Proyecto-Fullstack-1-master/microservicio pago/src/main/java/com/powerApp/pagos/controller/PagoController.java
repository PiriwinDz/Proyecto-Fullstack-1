package com.powerApp.pagos.controller;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.service.PagoService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
@Validated
public class PagoController {

    private final PagoService pagoService;

    // GET: listar todos los pagos
    @GetMapping
    public List<Pago> listar() {
        return pagoService.listar();
    }

    // GET: buscar por metodo de pago
    @GetMapping("/metodoPago/{metodoPago}")
    public List<Pago> buscarPorMetodoDePago(@PathVariable String metodoPago) {
        return pagoService.buscarPorMetodoDePago(metodoPago);
    }

    // GET: buscar por ID
    @GetMapping("/{id}")
    public Optional<Pago> buscarPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id);
    }

    // GET: buscar por usuarioId
    @GetMapping("/usuario/{usuarioId}")
    public List<Pago> buscarPorUsuario(@PathVariable Long usuarioId) {
        return pagoService.buscarPorUsuario(usuarioId);
    }

    // GET: buscar por estado
    @GetMapping("/estado/{estado}")
    public List<Pago> buscarPorEstado(@PathVariable EstadoPago estado) {
        return pagoService.buscarPorEstado(estado);
    }

    // GET: buscar por membresiaId
    @GetMapping("/membresia/{membresiaId}")
    public List<Pago> buscarPorMembresia(@PathVariable Long membresiaId) {
        return pagoService.buscarPorMembresia(membresiaId);
    }

    // POST: crear pago
    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesar(
            @Valid @RequestBody PagoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagoService.procesarPago(dto));
    }

    // PUT: actualizar estado del pago
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pago> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        Pago pagoActualizado = pagoService.actualizarEstado(
                id,
                EstadoPago.valueOf(estado.toUpperCase())
        );

        return ResponseEntity.ok(pagoActualizado);
    }

    // DELETE: eliminar pago por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        pagoService.eliminar(id);

        return ResponseEntity.ok("Pago eliminado correctamente");
    }

}