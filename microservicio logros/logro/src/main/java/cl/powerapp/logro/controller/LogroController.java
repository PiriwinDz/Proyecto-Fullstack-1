package cl.powerapp.logro.controller;

import cl.powerapp.logro.dto.DesbloquearLogroDTO;
import cl.powerapp.logro.dto.LogroListadoDTO;
import cl.powerapp.logro.dto.LogroSimpleDTO;
import cl.powerapp.logro.dto.UsuarioLogrosDTO;
import cl.powerapp.logro.model.Logro;
import cl.powerapp.logro.model.LogroUsuario;
import cl.powerapp.logro.service.LogroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
        summary = "Registrar o agregar un logro",
        description = "Permite registrar o agregar un nuevo logro a la lista"

    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Logro agregado correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos del logro inválidos"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<Logro> crearLogro(
            @Valid @RequestBody Logro logro) {

        Logro nuevo = service.guardar(logro);

        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(
        summary = "Desbloquear un logro",
        description = "Permite asociar un logro a un usuario cuando cumple los requisitos necesarios"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Logro desbloqueado correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                @ApiResponse(responseCode = "404", description = "Usuario o logro no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/desbloquear") 
    public ResponseEntity<LogroUsuario> desbloquearLogro(@Valid @RequestBody 
                                    DesbloquearLogroDTO dto){

            LogroUsuario nuevo = service.desbloquearLogro(dto);
            
            return ResponseEntity.status(201).body(nuevo); 
    }

    @Operation(
        summary = "Listar logros",
        description = "Obtiene una lista con todos los logros registrados en el sistema"

    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos invalidos"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<LogroListadoDTO> listarDTO() {

        return service.listarDTO();
    }

    @Operation(
        summary = "Obtener detalle simple de un logro",
        description = "Obtiene la información básica de un logro específico mediante su id"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Logro encontrado"),
                @ApiResponse(responseCode = "404", description = "Logro no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}/detalle-simple")
    public LogroSimpleDTO obtenerDetalleSimple(
            @PathVariable Long id) {

        return service.obtenerDetalleSimple(id);
    }

    @Operation(
        summary = "Obtener logros de un usuario",
        description = "Retorna todos los logros obtenidos por un usuario específico a traves de su id"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Información obtenida correctamente"),
                @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}")
    public UsuarioLogrosDTO obtenerLogrosUsuario(
            @PathVariable Long usuarioId) {

        return service.obtenerLogrosConUsuario(usuarioId);
    }

    @Operation(
        summary = "Actualizar un logro",
        description = "Permite modificar los datos de un logro existente"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Logro actualizado correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                @ApiResponse(responseCode = "404", description = "Logro no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Logro> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Logro logro) {

        Logro actualizado = service.actualizar(id, logro);

        return ResponseEntity.ok(actualizado);
    }

    @Operation(
        summary = "Eliminar un logro",
        description = "Elimina un logro existente utilizando su identificador"
    )
    @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Logro eliminado correctamente"),
                @ApiResponse(responseCode = "404", description = "Logro no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
