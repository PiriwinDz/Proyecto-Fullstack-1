package com.example.autenticacion.controller;

import com.example.autenticacion.dto.ActualizarUsuarioDTO;
import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.service.AutenticacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario en el sistema y devuelve un token JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autenticacionService.registrar(dto));
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario y devuelve un token JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(
                autenticacionService.login(dto));
    }

    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene el listado completo de usuarios registrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {

        return ResponseEntity.ok(
                autenticacionService.listarTodos());
    }

    @Operation(
            summary = "Buscar usuario por ID",
            description = "Obtiene la información de un usuario según su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                autenticacionService.buscarPorId(id));
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarUsuarioDTO dto) {

        return ResponseEntity.ok(
                autenticacionService.actualizar(id, dto));
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        autenticacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}