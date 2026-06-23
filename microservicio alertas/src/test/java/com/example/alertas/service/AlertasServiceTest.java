package com.example.alertas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.exception.AlertaNoEncontradaException;
import com.example.alertas.model.Alerta;
import com.example.alertas.repository.AlertaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @InjectMocks
    private AlertaService service;

    private Alerta alerta;

    @BeforeEach
    void setUp() {

        alerta = Alerta.builder()
                .id(1L)
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
    }

    @Test
    void buscarPorIdExistente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(alerta));

        var resultado = service.buscarPorId(1L);

        assertEquals("Alerta Test", resultado.getTitulo());
    }

    @Test
    void buscarPorIdNoExistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlertaNoEncontradaException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void crearAlerta() {

        AlertaRequestDTO dto = new AlertaRequestDTO();
        dto.setTitulo("Nueva alerta");
        dto.setMensaje("Mensaje");
        dto.setTipo("INFO");

        when(repository.save(any(Alerta.class)))
                .thenReturn(alerta);

        var resultado = service.crear(dto);

        assertEquals("Alerta Test", resultado.getTitulo());
    }

    @Test
    void desactivarAlerta() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(alerta));

        when(repository.save(any(Alerta.class)))
                .thenReturn(alerta);

        var resultado = service.desactivar(1L);

        assertFalse(resultado.getActiva());
    }

    @Test
    void desactivarAlertaNoExistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlertaNoEncontradaException.class,
                () -> service.desactivar(99L));
    }
}
