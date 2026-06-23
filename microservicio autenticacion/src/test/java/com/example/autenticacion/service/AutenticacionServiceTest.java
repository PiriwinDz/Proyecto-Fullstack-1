package com.example.autenticacion.service;

import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.model.RolUsuario;
import com.example.autenticacion.model.Usuario;
import com.example.autenticacion.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AutenticacionService autenticacionService;

    @Test
    void registrarUsuarioExitosamente() {

        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setNombre("Matias");
        dto.setCorreo("matias@test.cl");
        dto.setPassword("123456");
        dto.setRol(RolUsuario.ATLETA);

        Usuario usuarioGuardado = Usuario.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .password("passwordEncriptada")
                .rol(RolUsuario.ATLETA)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);
        when(jwtService.generarToken(any(), any(), any())).thenReturn("token-test");

        var resultado = autenticacionService.registrar(dto);

        assertNotNull(resultado);
        assertEquals("Matias", resultado.getNombre());
        assertEquals("matias@test.cl", resultado.getCorreo());
        assertEquals("token-test", resultado.getToken());
    }

    @Test
    void registrarCorreoDuplicadoDebeLanzarExcepcion() {

        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setCorreo("existente@test.cl");

        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> autenticacionService.registrar(dto)
        );

        assertTrue(exception.getMessage().contains("Ya existe una cuenta"));
    }

    @Test
    void buscarPorIdExistente() {

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .password("123456")
                .rol(RolUsuario.ATLETA)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(java.util.Optional.of(usuario));

        UsuarioResponseDTO resultado =
                autenticacionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Matias", resultado.getNombre());
    }

    @Test
    void desactivarUsuarioExistente() {

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .password("123456")
                .rol(RolUsuario.ATLETA)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(java.util.Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        UsuarioResponseDTO resultado =
                autenticacionService.desactivar(1L);

        assertNotNull(resultado);
        assertFalse(resultado.getActivo());
    }

    @Test
    void loginExitoso() {

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setCorreo("matias@test.cl");
        dto.setPassword("123456");

        String passwordEncriptada =
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                        .encode("123456");

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .password(passwordEncriptada)
                .rol(RolUsuario.ATLETA)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        when(usuarioRepository.findByCorreo(dto.getCorreo()))
                .thenReturn(java.util.Optional.of(usuario));

        when(jwtService.generarToken(any(), any(), any()))
                .thenReturn("token-login");

        var resultado = autenticacionService.login(dto);

        assertNotNull(resultado);
        assertEquals("token-login", resultado.getToken());
        assertEquals("matias@test.cl", resultado.getCorreo());
    }

}


