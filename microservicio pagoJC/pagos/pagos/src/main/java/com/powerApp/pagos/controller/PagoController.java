package com.powerApp.pagos.controller;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
@Validated
public class PagoController {

        private final PagoService pagoService;

        @Operation(summary = "Listar todos los pagos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
        })
        @GetMapping
        public List<Pago> listar() {
                return pagoService.listar();
        }

        @Operation(summary = "Buscar pago por ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
                        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
        })
        @GetMapping("/{id}")
        public Optional<Pago> buscarPorId(@PathVariable Long id) {
                return pagoService.buscarPorId(id);
        }

        @Operation(summary = "Buscar pagos por usuario")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagos encontrados para el usuario")
        })
        @GetMapping("/usuario/{usuarioId}")
        public List<Pago> buscarPorUsuario(@PathVariable Long usuarioId) {
                return pagoService.buscarPorUsuario(usuarioId);
        }

        @Operation(summary = "Buscar pagos por membresía")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagos encontrados para la membresía")
        })
        @GetMapping("/membresia/{membresiaId}")
        public List<Pago> buscarPorMembresia(@PathVariable Long membresiaId) {
                return pagoService.buscarPorMembresia(membresiaId);
        }

        @Operation(summary = "Buscar pagos por estado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagos encontrados con el estado indicado")
        })
        @GetMapping("/estado/{estado}")
        public List<Pago> buscarPorEstado(@PathVariable String estado) {
                return pagoService.buscarPorEstado(estado);
        }

        @Operation(summary = "Buscar pagos por método de pago")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagos encontrados con el método indicado")
        })
        @GetMapping("/metodo/{metodoPago}")
        public List<Pago> buscarPorMetodo(@PathVariable String metodoPago) {
                return pagoService.buscarPorMetodoPago(metodoPago);
        }

        @Operation(summary = "Crear un nuevo pago")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
        })
        @PostMapping
        public ResponseEntity<PagoResponseDTO> crearPago(
                        @Valid @RequestBody PagoRequestDTO dto) {
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(pagoService.crearPago(dto));
        }

        @Operation(summary = "Actualizar un pago existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
        })
        @PutMapping("/{id}")
        public ResponseEntity<Pago> actualizar(
                        @PathVariable Long id,
                        @RequestBody Pago pago) {
                Pago pagoActualizado = pagoService.actualizar(id, pago);
                return ResponseEntity.ok(pagoActualizado);
        }

        @Operation(summary = "Eliminar un pago")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(@PathVariable Long id) {
                pagoService.eliminar(id);
                return ResponseEntity.ok("Pago eliminado correctamente");
        }
}
