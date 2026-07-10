package com.example.autenticacion.service;

import com.example.autenticacion.dto.ActualizarUsuarioDTO;
import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.exception.CorreoYaRegistradoException;
import com.example.autenticacion.exception.CredencialesInvalidasException;
import com.example.autenticacion.exception.UsuarioNoEncontradoException;
import com.example.autenticacion.model.RolUsuario;
import com.example.autenticacion.model.Usuario;
import com.example.autenticacion.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AutenticacionService autenticacionService;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {

        usuario = Usuario.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .password("passwordEncriptada")
                .rol(RolUsuario.ATLETA)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

    }

    @Test
    void registrarUsuarioCorrectamente() {

        RegisterRequestDTO dto = new RegisterRequestDTO(
                "Matias",
                "matias@test.cl",
                "Matias123",
                RolUsuario.ATLETA
                );

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("passwordEncriptada");
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(jwtService.generarToken(anyLong(), anyString(), anyString()))
                .thenReturn("token");

        assertDoesNotThrow(() -> autenticacionService.registrar(dto));

        verify(usuarioRepository).save(any());
    }

    @Test
    void registrarCorreoDuplicado() {

        RegisterRequestDTO dto = new RegisterRequestDTO(
                "Matias",
                "matias@test.cl",
                "12345678",
                RolUsuario.ATLETA
        );

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        assertThrows(
                CorreoYaRegistradoException.class,
                () -> autenticacionService.registrar(dto)
        );
    }

    @Test
    void loginCorrecto() {

        LoginRequestDTO dto = new LoginRequestDTO(
                "matias@test.cl",
                "12345678"
        );

        when(usuarioRepository.findByCorreo(dto.getCorreo()))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches(dto.getPassword(), usuario.getPassword()))
                .thenReturn(true);

        when(jwtService.generarToken(anyLong(), anyString(), anyString()))
                .thenReturn("token");

        assertDoesNotThrow(() -> autenticacionService.login(dto));
    }

    @Test
    void loginCredencialesIncorrectas() {

        LoginRequestDTO dto = new LoginRequestDTO(
                "matias@test.cl",
                "12345678"
        );

        when(usuarioRepository.findByCorreo(dto.getCorreo()))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        assertThrows(
                CredencialesInvalidasException.class,
                () -> autenticacionService.login(dto)
        );
    }

    @Test
    void buscarUsuarioPorId() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        assertNotNull(
                autenticacionService.buscarPorId(1L)
        );
    }

    @Test
    void buscarUsuarioNoExiste() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNoEncontradoException.class,
                () -> autenticacionService.buscarPorId(1L)
        );
    }

    @Test
    void listarUsuarios() {

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario));

        assertEquals(
                1,
                autenticacionService.listarTodos().size()
        );
    }

    @Test
    void actualizarUsuario() {

        ActualizarUsuarioDTO dto = new ActualizarUsuarioDTO(
                "Nuevo Nombre",
                "nuevo@test.cl",
                RolUsuario.ADMINISTRADOR
        );

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.existsByCorreo(dto.getCorreo()))
                .thenReturn(false);

        when(usuarioRepository.save(any()))
                .thenReturn(usuario);

        assertDoesNotThrow(() ->
                autenticacionService.actualizar(1L, dto));
    }

    @Test
    void eliminarUsuario() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() ->
                autenticacionService.eliminar(1L));

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void eliminarUsuarioNoExiste() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNoEncontradoException.class,
                () -> autenticacionService.eliminar(1L)
        );
    }

}