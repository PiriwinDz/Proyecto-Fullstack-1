package com.example.sede.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.sede.dto.SedeRequestDTO;
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
    void eliminarSedeCorrectamente() {

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

        doNothing().when(repository).delete(sede);

        service.eliminar(1L);

        verify(repository).delete(sede);
    }

    @Test
    void eliminarSedeNoExistente() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                SedeNoEncontradaException.class,
                () -> service.eliminar(1L));
    }

    @Test
    void buscarPorIdInexistente() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                SedeNoEncontradaException.class,
                () -> service.buscarPorId(999L));
    }
        @Test
        void listarSedes() {

        Sede sede = Sede.builder()
            .id(1L)
            .nombre("Sede Central")
            .direccion("Santiago")
            .horario("08:00-22:00")
            .capacidadMaxima(100)
            .ocupacionActual(20)
            .activo(true)
            .creadoEn(LocalDateTime.now())
            .build();

        when(repository.findByActivoTrue())
            .thenReturn(java.util.List.of(sede));

        var respuesta = service.listar();

        assertEquals(1, respuesta.size());
        assertEquals("Sede Central", respuesta.get(0).getNombre());
     }

        @Test
        void buscarPorIdCorrectamente() {

        Sede sede = Sede.builder()
            .id(1L)
            .nombre("Sede Central")
            .direccion("Santiago")
            .horario("08:00-22:00")
            .capacidadMaxima(100)
            .ocupacionActual(20)
            .activo(true)
            .creadoEn(LocalDateTime.now())
            .build();

        when(repository.findById(1L))
            .thenReturn(Optional.of(sede));

        var respuesta = service.buscarPorId(1L);

        assertEquals(1L, respuesta.getId());
        assertEquals("Sede Central", respuesta.getNombre());
     }

        @Test
        void crearSedeCorrectamente() {

        SedeRequestDTO dto = new SedeRequestDTO(
        "Nueva Sede",
        "Las Condes",
        "09:00-21:00",
        150
        );

        Sede sede = Sede.builder()
            .id(1L)
            .nombre(dto.getNombre())
            .direccion(dto.getDireccion())
            .horario(dto.getHorario())
            .capacidadMaxima(dto.getCapacidadMaxima())
            .ocupacionActual(0)
            .activo(true)
            .creadoEn(LocalDateTime.now())
            .build();

        when(repository.save(any(Sede.class)))
            .thenReturn(sede);

        var respuesta = service.crear(dto);

        assertEquals("Nueva Sede", respuesta.getNombre());
        assertEquals(150, respuesta.getCapacidadMaxima());
   }

        @Test
        void actualizarSedeCorrectamente() {

        SedeRequestDTO dto = new SedeRequestDTO(
    "Sede Actualizada",
    "Providencia",
    "10:00-20:00",
    120
        );

        Sede sede = Sede.builder()
            .id(1L)
            .nombre("Antigua")
            .direccion("Vieja")
            .horario("08:00-22:00")
            .capacidadMaxima(100)
            .ocupacionActual(10)
            .activo(true)
            .creadoEn(LocalDateTime.now())
            .build();

        when(repository.findById(1L))
            .thenReturn(Optional.of(sede));

        when(repository.save(any(Sede.class)))
            .thenReturn(sede);

        var respuesta = service.actualizar(1L, dto);

        assertEquals("Sede Actualizada", respuesta.getNombre());
        assertEquals("Providencia", respuesta.getDireccion());
        assertEquals(120, respuesta.getCapacidadMaxima());
     }

}