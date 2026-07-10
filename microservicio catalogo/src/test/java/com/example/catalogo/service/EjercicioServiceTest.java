package com.example.catalogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.exception.EjercicioNoEncontradoException;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.repository.EjercicioRepository;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceTest {

    @Mock
    private EjercicioRepository ejercicioRepository;

    @InjectMocks
    private EjercicioService ejercicioService;

    @Test
    void crearEjercicio() {

        EjercicioDTO dto = new EjercicioDTO(
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioRepository.save(any(Ejercicio.class)))
                .thenReturn(ejercicio);

        Ejercicio resultado = ejercicioService.crear(dto);

        assertNotNull(resultado);
        assertEquals("Press de Banca", resultado.getNombre());

        verify(ejercicioRepository).save(any(Ejercicio.class));
    }

    @Test
    void listarEjercicios() {

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioRepository.findAll())
                .thenReturn(List.of(ejercicio));

        List<Ejercicio> resultados = ejercicioService.listar();

        assertEquals(1, resultados.size());
        assertEquals("Press de Banca", resultados.get(0).getNombre());
    }

    @Test
    void listarEjerciciosVacio() {

        when(ejercicioRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Ejercicio> resultados = ejercicioService.listar();

        assertNotNull(resultados);
        assertEquals(0, resultados.size());
    }

    @Test
    void buscarEjercicioPorId() {

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioRepository.findById(1L))
                .thenReturn(Optional.of(ejercicio));

        Ejercicio resultado = ejercicioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Press de Banca", resultado.getNombre());
    }

    @Test
    void buscarEjercicioNoExistente() {

        when(ejercicioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                EjercicioNoEncontradoException.class,
                () -> ejercicioService.buscarPorId(99L));
    }

    @Test
    void actualizarEjercicio() {

        EjercicioDTO dto = new EjercicioDTO(
                "Press Inclinado",
                "Pecho",
                "Actualizado"
        );

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio original"
        );

        when(ejercicioRepository.findById(1L))
                .thenReturn(Optional.of(ejercicio));

        when(ejercicioRepository.save(any(Ejercicio.class)))
                .thenReturn(ejercicio);

        Ejercicio resultado = ejercicioService.actualizar(1L, dto);

        assertEquals("Press Inclinado", resultado.getNombre());
        assertEquals("Pecho", resultado.getGrupoMuscular());
        assertEquals("Actualizado", resultado.getDescripcion());
    }

    @Test
    void eliminarEjercicio() {

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioRepository.findById(1L))
                .thenReturn(Optional.of(ejercicio));

        doNothing().when(ejercicioRepository).delete(ejercicio);

        ejercicioService.eliminar(1L);

        verify(ejercicioRepository).delete(ejercicio);
    }

    @Test
    void eliminarEjercicioNoExistente() {

        when(ejercicioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                EjercicioNoEncontradoException.class,
                () -> ejercicioService.eliminar(99L));
    }

}