package cl.tickets.soporte.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.tickets.soporte.dto.TicketListadoDTO;
import cl.tickets.soporte.dto.TicketSimpleDTO;
import cl.tickets.soporte.dto.UsuarioTicketsDTO;
import cl.tickets.soporte.model.TicketSoporte;
import cl.tickets.soporte.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @Operation(
        summary = "Crear ticket de soporte",
        description = "Permite registrar un nuevo ticket de soporte asociado a un usuario"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Ticket creado correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/usuario/{usuarioId}/agregar")
    public ResponseEntity<TicketSoporte> crearTicket(
            @PathVariable Long usuarioId,
            @Valid @RequestBody TicketSoporte ticket) {

        TicketSoporte nuevo = service.guardar(usuarioId, ticket);

        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(
        summary = "Listar tickets",
        description = "Obtiene una lista con todos los tickets de soporte registrados"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<TicketListadoDTO> listarDTO() {

        return service.listarDTO();
    }

    @Operation(
        summary = "Obtener detalle simple de un ticket",
        description = "Obtiene la información básica de un ticket mediante su id"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
                @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}/detalle-simple")
    public TicketSimpleDTO obtenerDetalleSimple(
            @PathVariable Long id) {

        return service.obtenerDetalleSimple(id);
    }

    @Operation(
        summary = "Obtener tickets de un usuario",
        description = "Retorna todos los tickets asociados a un usuario específico"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Información obtenida correctamente"),
                @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}")
    public UsuarioTicketsDTO obtenerTicketsUsuario(
            @PathVariable Long usuarioId) {

        return service.obtenerTicketsConUsuario(usuarioId);
    }

    @Operation(
        summary = "Actualizar ticket",
        description = "Permite modificar la información de un ticket existente"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Ticket actualizado correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TicketSoporte> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TicketSoporte ticket) {

        TicketSoporte actualizado = service.actualizar(id, ticket);

        return ResponseEntity.ok(actualizado);
    }

    @Operation(
        summary = "Eliminar ticket",
        description = "Permite eliminar un ticket de soporte mediante su id"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Ticket eliminado correctamente"),
                @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}