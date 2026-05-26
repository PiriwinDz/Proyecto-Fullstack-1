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

@RestController 
@RequestMapping("/api/sedes") 
@RequiredArgsConstructor 
public class SedeController {

    private final SedeService sedeService;

    
    
    @GetMapping
    public ResponseEntity<List<SedeResponseDTO>> listar() {
        return ResponseEntity.ok(sedeService.listar()); 
    }

    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.buscarPorId(id)); 
    }

    
    
    
    @PostMapping
    public ResponseEntity<SedeResponseDTO> crear(@Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) 
                .body(sedeService.crear(dto));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SedeRequestDTO dto) {
        return ResponseEntity.ok(sedeService.actualizar(id, dto)); 
    }

    
    
    @PutMapping("/{id}/entrada")
    public ResponseEntity<SedeResponseDTO> registrarEntrada(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarEntrada(id)); 
    }

    
    
    @PutMapping("/{id}/salida")
    public ResponseEntity<SedeResponseDTO> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.registrarSalida(id)); 
    }

    
    
    
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<SedeResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.desactivar(id)); 
    }
}
