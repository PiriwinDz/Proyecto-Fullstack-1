package cl.tickets.soporte.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.tickets.soporte.dto.TicketListadoDTO;
import cl.tickets.soporte.dto.TicketSimpleDTO;
import cl.tickets.soporte.dto.UsuarioTicketsDTO;
import cl.tickets.soporte.model.TicketSoporte;
import cl.tickets.soporte.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service){
        this.service = service;
    }

    @PostMapping("/usuario/{usuarioId}/agregar")
    public ResponseEntity<TicketSoporte> crearTicket(@PathVariable Long usuarioId,
                        @Valid @RequestBody TicketSoporte ticket){

        TicketSoporte nuevo = service.guardar(usuarioId, ticket);

        return ResponseEntity.status(201).body(nuevo);

}

    @GetMapping("/listar")
    public List<TicketListadoDTO> listarDTO(){

        return service.listarDTO();
    }

    @GetMapping("/{id}/detalle-simple")
    public TicketSimpleDTO obtenerDetalleSimple(
            @PathVariable Long id){

        return service.obtenerDetalleSimple(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public UsuarioTicketsDTO obtenerTicketsUsuario(
            @PathVariable Long usuarioId){

        return service
                .obtenerTicketsConUsuario(
                        usuarioId);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TicketSoporte> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody
            TicketSoporte ticket){

        TicketSoporte actualizado = service.actualizar(id, ticket);

        return ResponseEntity
                .ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void>
    eliminar(@PathVariable Long id){

        service.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}


