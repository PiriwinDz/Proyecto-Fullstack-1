package com.example.sede.controller;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.service.SedeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indica que esta clase maneja peticiones HTTP y devuelve JSON
@RequestMapping("/api/v1/sedes") // prefijo base para todos los endpoints de este controller
@RequiredArgsConstructor // inyecta el service automaticamente sin necesidad de @Autowired
public class SedeController {

    private final SedeService sedeService;

    // GET /api/v1/sedes
    // retorna todas las sedes activas del sistema
    @GetMapping
    public ResponseEntity<List<SedeResponseDTO>> listar() {
        return ResponseEntity.ok(sedeService.listar()); // 200 con la lista de sedes
    }

    // GET /api/v1/sedes/{id}
    // @PathVariable extrae el {id} de la URL
    // retorna una sede especifica, 404 si no existe
    @GetMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.buscarPorId(id)); // 200 con la sede encontrada
    }

    // POST /api/v1/sedes
    // @Valid activa las validaciones del SedeRequestDTO antes de entrar al metodo
    // @RequestBody convierte el JSON del body en un objeto SedeRequestDTO
    @PostMapping
    public ResponseEntity<SedeResponseDTO> crear(@Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) // 201 porque se creo un recurso nuevo
                .body(sedeService.crear(dto));
    }

    // PUT /api/v1/sedes/{id}
    // actualiza los datos de una sede existente
    @PutMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.ok(sedeService.actualizar(id, dto)); // 200 con la sede actualizada
    }

    // PUT /api/v1/sedes/{id}/entrada
    // registra que una persona entro a la sede, incrementa la ocupacion
    @PutMapping("/{id}/entrada")
    public ResponseEntity<SedeResponseDTO> registrarEntrada(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarEntrada(id)); // 200 con la sede actualizada
    }

    // PUT /api/v1/sedes/{id}/salida
    // registra que una persona salio de la sede, decrementa la ocupacion
    @PutMapping("/{id}/salida")
    public ResponseEntity<SedeResponseDTO> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarSalida(id)); // 200 con la sede actualizada
    }

    // PUT /api/v1/sedes/{id}/desactivar
    // desactiva una sede sin borrarla de la BD (soft delete)
    // solo el administrador deberia poder llamar este endpoint
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<SedeResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.desactivar(id)); // 200 con la sede desactivada
    }
}
