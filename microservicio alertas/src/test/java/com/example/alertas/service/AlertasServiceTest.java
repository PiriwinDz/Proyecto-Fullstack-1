package com.example.alertas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.dto.UsuarioDTO;
import com.example.alertas.exception.AlertaNoEncontradaException;
import com.example.alertas.exception.UsuarioNoEncontradoException;
import com.example.alertas.model.Alerta;
import com.example.alertas.repository.AlertaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AlertaService service;
    private Alerta alerta;
    private UsuarioDTO usuario;

    @BeforeEach
    void setUp() {

        usuario = UsuarioDTO.builder()
                .id(1L)
                .nombre("José")
                .correo("jose@test.cl")
                .rol("CLIENTE")
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        alerta = Alerta.builder()
                .id(1L)
                .usuarioId(1L)
                .titulo("Alerta Test")
                .mensaje("Mensaje Test")
                .tipo("INFO")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();
    }

    @Test
    void listarAlertas() {

        when(repository.findAll()).thenReturn(List.of(alerta));

        var resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Alerta Test", resultado.get(0).getTitulo());

        verify(repository).findAll();
    }

    @Test
    void buscarPorIdExistente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(alerta));

        var resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Alerta Test", resultado.getTitulo());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorIdNoExistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlertaNoEncontradaException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }

    @Test
    void crearAlerta() {

        AlertaRequestDTO dto = new AlertaRequestDTO();
        dto.setUsuarioId(1L);
        dto.setTitulo("Nueva alerta");
        dto.setMensaje("Mensaje");
        dto.setTipo("INFO");

        when(restTemplate.getForObject(
                anyString(),
                eq(UsuarioDTO.class)))
                .thenReturn(usuario);

        when(repository.save(any(Alerta.class)))
                .thenReturn(alerta);

        var resultado = service.crear(dto);

        assertNotNull(resultado);
        assertEquals("Alerta Test", resultado.getTitulo());

        verify(repository).save(any(Alerta.class));
    }

    @Test
    void crearAlertaUsuarioNoExiste() {

        AlertaRequestDTO dto = new AlertaRequestDTO();
        dto.setUsuarioId(99L);
        dto.setTitulo("Nueva");
        dto.setMensaje("Mensaje");
        dto.setTipo("INFO");

        when(restTemplate.getForObject(
                anyString(),
                eq(UsuarioDTO.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(
                UsuarioNoEncontradoException.class,
                () -> service.crear(dto));

        verify(repository, never()).save(any());
    }

    @Test
    void actualizarAlerta() {

        AlertaRequestDTO dto = new AlertaRequestDTO();
        dto.setUsuarioId(1L);
        dto.setTitulo("Actualizada");
        dto.setMensaje("Nuevo mensaje");
        dto.setTipo("ERROR");

        when(repository.findById(1L))
                .thenReturn(Optional.of(alerta));

        when(restTemplate.getForObject(
                anyString(),
                eq(UsuarioDTO.class)))
                .thenReturn(usuario);

        when(repository.save(any(Alerta.class)))
                .thenReturn(alerta);

        var resultado = service.actualizar(1L, dto);

        assertNotNull(resultado);

        verify(repository).save(any(Alerta.class));
    }

    @Test
    void actualizarAlertaNoExiste() {

        AlertaRequestDTO dto = new AlertaRequestDTO();
        dto.setUsuarioId(1L);

        when(repository.findById(50L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlertaNoEncontradaException.class,
                () -> service.actualizar(50L, dto));

        verify(repository, never()).save(any());
    }

    @Test
    void eliminarAlerta() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(alerta));

        service.eliminar(1L);

        verify(repository).delete(alerta);
    }

    @Test
    void eliminarAlertaNoExiste() {

        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlertaNoEncontradaException.class,
                () -> service.eliminar(100L));

        verify(repository, never()).delete(any());
    }

}