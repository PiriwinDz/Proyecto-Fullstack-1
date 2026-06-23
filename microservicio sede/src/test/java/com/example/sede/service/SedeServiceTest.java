package com.example.sede.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sede.exception.SedeNoEncontradaException;
import com.example.sede.model.Sede;
import com.example.sede.repository.SedeRepository;

@ExtendWith(MockitoExtension.class)
class SedeServiceTest {

    @Mock
    private SedeRepository repository;

    @InjectMocks
    private SedeService service;

    @Test
    void registrarEntradaCorrectamente() {

        Sede sede = Sede.builder()
                .id(1L)
                .nombre("Sede Central")
                .capacidadMaxima(100)
                .ocupacionActual(10)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(sede));

        when(repository.save(any(Sede.class)))
                .thenReturn(sede);

        var response = service.registrarEntrada(1L);

        assertEquals(11, response.getOcupacionActual());
    }

    @Test
    void registrarEntradaCapacidadLlena() {

        Sede sede = Sede.builder()
                .id(1L)
                .capacidadMaxima(100)
                .ocupacionActual(100)
                .activo(true)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(sede));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarEntrada(1L));
    }

    @Test
    void registrarSalidaCorrectamente() {

        Sede sede = Sede.builder()
                .id(1L)
                .capacidadMaxima(100)
                .ocupacionActual(10)
                .activo(true)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(sede));

        when(repository.save(any(Sede.class)))
                .thenReturn(sede);

        var response = service.registrarSalida(1L);

        assertEquals(9, response.getOcupacionActual());
    }

    @Test
    void registrarSalidaConOcupacionCero() {

        Sede sede = Sede.builder()
                .id(1L)
                .capacidadMaxima(100)
                .ocupacionActual(0)
                .activo(true)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(sede));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarSalida(1L));
    }

    @Test
    void desactivarSedeCorrectamente() {

        Sede sede = Sede.builder()
            .id(1L)
            .nombre("Sede Centro")
            .direccion("Av. Principal 123")
            .horario("08:00 - 22:00")
            .capacidadMaxima(100)
            .ocupacionActual(20)
            .activo(true)
            .build();

        when(repository.findById(1L))
            .thenReturn(Optional.of(sede));

        when(repository.save(any(Sede.class)))
            .thenReturn(sede);

        var response = service.desactivar(1L);

        assertFalse(response.getActivo());
    }

    @Test
    void desactivarSedeYaDesactivada() {

        Sede sede = Sede.builder()
                .id(1L)
                .activo(false)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(sede));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.desactivar(1L));
    }

    @Test
    void buscarPorIdInexistente() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                SedeNoEncontradaException.class,
                () -> service.buscarPorId(999L));
    }
}
