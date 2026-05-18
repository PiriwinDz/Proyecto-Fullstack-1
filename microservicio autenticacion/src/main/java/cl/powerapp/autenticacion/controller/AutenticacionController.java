package cl.powerapp.autenticacion.controller;

import cl.powerapp.autenticacion.dto.*;
import cl.powerapp.autenticacion.service.AutenticacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    // POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autenticacionService.registrar(dto));
    }

    // POST /api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(autenticacionService.login(dto));
    }

    // GET /api/v1/auth/usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(autenticacionService.listarTodos());
    }

    // GET /api/v1/auth/usuarios/{id}
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.buscarPorId(id));
    }

    // PUT /api/v1/auth/usuarios/{id}/desactivar
    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<UsuarioResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(autenticacionService.desactivar(id));
    }
}
