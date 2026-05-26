package com.example.sede.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.service.SedeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // indica que esta clase maneja peticiones HTTP y devuelve JSON
@RequestMapping("/api/sedes") // prefijo base para todos los endpoints de este controller
@RequiredArgsConstructor // inyecta el service automaticamente sin necesidad de @Autowired
public class SedeController {

    private final SedeService sedeService;

    // GET /api/sedes
    // retorna todas las sedes activas del sistema
    @GetMapping
    public ResponseEntity<List<SedeResponseDTO>> listar() {
        return ResponseEntity.ok(sedeService.listar()); // 200 con la lista de sedes
    }

    // GET /api/sedes/{id}
    // @PathVariable extrae el {id} de la URL
    // retorna una sede especifica, 404 si no existe
    @GetMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.buscarPorId(id)); // 200 con la sede encontrada
    }

    // POST /api/sedes
    // @Valid activa las validaciones del SedeRequestDTO antes de entrar al metodo
    // @RequestBody convierte el JSON del body en un objeto SedeRequestDTO
    @PostMapping
    public ResponseEntity<SedeResponseDTO> crear(@Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) // 201 porque se creo un recurso nuevo
                .body(sedeService.crear(dto));
    }

    // PUT /api/sedes/{id}
    // actualiza los datos de una sede existente
    @PutMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.ok(sedeService.actualizar(id, dto)); // 200 con la sede actualizada
    }

    // PUT /api/sedes/{id}/entrada
    // registra que una persona entro a la sede, incrementa la ocupacion
    @PutMapping("/{id}/entrada")
    public ResponseEntity<SedeResponseDTO> registrarEntrada(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarEntrada(id)); // 200 con la sede actualizada
    }

    // PUT /api/sedes/{id}/salida
    // registra que una persona salio de la sede, decrementa la ocupacion
    @PutMapping("/{id}/salida")
    public ResponseEntity<SedeResponseDTO> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarSalida(id)); // 200 con la sede actualizada
    }

    // PUT /api/sedes/{id}/desactivar
    // desactiva una sede sin borrarla de la BD (soft delete)
    // solo el administrador deberia poder llamar este endpoint
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<SedeResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.desactivar(id)); // 200 con la sede desactivada
    }
}
