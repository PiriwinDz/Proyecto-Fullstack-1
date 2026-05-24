package cl.powerapp.logro.controller;

import cl.powerapp.logro.dto.DesbloquearLogroDTO;
import cl.powerapp.logro.dto.LogroListadoDTO;
import cl.powerapp.logro.dto.LogroSimpleDTO;
import cl.powerapp.logro.dto.UsuarioLogrosDTO;
import cl.powerapp.logro.model.Logro;
import cl.powerapp.logro.model.LogroUsuario;
import cl.powerapp.logro.service.LogroService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logros")
public class LogroController {

    private final LogroService service;

    public LogroController(LogroService service) {
        this.service = service;
    }

    @PostMapping("/agregar")
    public ResponseEntity<Logro> crearLogro(
            @Valid @RequestBody Logro logro) {

        Logro nuevo = service.guardar(logro);

        return ResponseEntity.status(201).body(nuevo);
    }

    @PostMapping("/desbloquear") 
    public ResponseEntity<LogroUsuario> desbloquearLogro(@RequestBody 
                                    DesbloquearLogroDTO dto){

            LogroUsuario nuevo = service.desbloquearLogro(dto);
            
            return ResponseEntity.status(201).body(nuevo); 
    }

    @GetMapping("/listar")
    public List<LogroListadoDTO> listarDTO() {

        return service.listarDTO();
    }

    @GetMapping("/{id}/detalle-simple")
    public LogroSimpleDTO obtenerDetalleSimple(
            @PathVariable Long id) {

        return service.obtenerDetalleSimple(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public UsuarioLogrosDTO obtenerLogrosUsuario(
            @PathVariable Long usuarioId) {

        return service.obtenerLogrosConUsuario(usuarioId);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Logro> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Logro logro) {

        Logro actualizado = service.actualizar(id, logro);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}