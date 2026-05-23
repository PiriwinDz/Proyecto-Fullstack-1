package cl.powerapp.pagos.controller;

import cl.powerapp.pagos.dto.PagoRequestDTO;
import cl.powerapp.pagos.dto.PagoResponseDTO;
import cl.powerapp.pagos.model.Pago;
import cl.powerapp.pagos.service.PagoService;

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

    // GET: buscar pago por ID
    @GetMapping("/{id}")
    public Optional<Pago> buscarPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id);
    }

    // GET: buscar pagos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Pago> buscarPorUsuario(@PathVariable Long usuarioId) {
        return pagoService.buscarPorUsuario(usuarioId);
    }

    // GET: buscar pagos por membresía
    @GetMapping("/membresia/{membresiaId}")
    public List<Pago> buscarPorMembresia(@PathVariable Long membresiaId) {
        return pagoService.buscarPorMembresia(membresiaId);
    }

    // GET: buscar pagos por estado
    @GetMapping("/estado/{estado}")
    public List<Pago> buscarPorEstado(@PathVariable String estado) {
        return pagoService.buscarPorEstado(estado);
    }

    // GET: buscar pagos por método de pago
    @GetMapping("/metodo/{metodoPago}")
    public List<Pago> buscarPorMetodo(@PathVariable String metodoPago) {
        return pagoService.buscarPorMetodoPago(metodoPago);
    }

    // POST: crear pago
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(
            @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagoService.crearPago(dto));
    }

    // PUT: actualizar pago
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @PathVariable Long id,
            @RequestBody Pago pago) {
        Pago pagoActualizado = pagoService.actualizar(id, pago);
        return ResponseEntity.ok(pagoActualizado);
    }

    // DELETE: eliminar pago
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}

