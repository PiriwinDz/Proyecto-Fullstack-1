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

@RestController // indica que esta clase maneja peticiones HTTP y devuelve JSON
@RequestMapping("/api/auth") // prefijo base para todos los endpoints de este controller
@RequiredArgsConstructor // inyecta el service automaticamente
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    // POST /api/auth/register
    // @Valid activa las validaciones del RegisterRequestDTO antes de entrar al metodo
    // @RequestBody convierte el JSON del body en un objeto RegisterRequestDTO
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED) // devuelve 201 porque se creo un recurso nuevo
                .body(autenticacionService.registrar(dto));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(autenticacionService.login(dto)); // devuelve 200
    }

    // GET /api/auth/usuarios
    // endpoint para que el administrador vea todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(autenticacionService.listarTodos()); // devuelve 200 con la lista
    }

    // GET /api/auth/usuarios/{id}
    // @PathVariable extrae el {id} de la URL
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.buscarPorId(id)); // devuelve 200
    }

    // PUT /api/auth/usuarios/{id}/desactivar
    // desactiva un usuario sin borrarlo, solo el administrador deberia llamar esto
    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<UsuarioResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.desactivar(id)); // devuelve 200
    }
}
