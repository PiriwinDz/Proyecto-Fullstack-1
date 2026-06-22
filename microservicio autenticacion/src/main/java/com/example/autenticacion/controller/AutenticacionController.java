package com.example.autenticacion.controller;

import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.service.AutenticacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  
@RequestMapping("/auth") 
@RequiredArgsConstructor 
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    
    
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) 
                .body(autenticacionService.registrar(dto));
    }

    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(autenticacionService.login(dto)); 
    }

    
    
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(autenticacionService.listarTodos()); 
    }

    
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.buscarPorId(id)); 
    }

    
    
    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<UsuarioResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.desactivar(id)); 
    }
}
