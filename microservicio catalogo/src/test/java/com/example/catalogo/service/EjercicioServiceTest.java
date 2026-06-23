package com.example.catalogo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.repository.EjercicioRepository;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceTest {

    @Mock
    private EjercicioRepository ejercicioRepository;

    @InjectMocks
    private EjercicioService ejercicioService;

    private EjercicioDTO ejercicioDTO;
    private Ejercicio ejercicio;

    @BeforeEach
    void setUp() {
        ejercicioDTO = new EjercicioDTO("Press de Banca", "Pecho", "Ejercicio de pecho con barra.");
        ejercicio = new Ejercicio(1L, "Press de Banca", "Pecho", "Ejercicio de pecho con barra.");
    }

    @Test
    void testGuardarEjercicio() {
        // Arrange
        when(ejercicioRepository.save(any(Ejercicio.class))).thenReturn(ejercicio);

        // Act
        Ejercicio resultado = ejercicioService.guardarEjercicio(ejercicioDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Press de Banca", resultado.getNombre());
        verify(ejercicioRepository).save(any(Ejercicio.class)); // Verifica que el método save fue llamado
    }

    @Test
    void testListarTodo_ConResultados() {
        // Arrange
        when(ejercicioRepository.findAll()).thenReturn(List.of(ejercicio));

        // Act
        List<Ejercicio> resultados = ejercicioService.listarTodo();

        // Assert
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Press de Banca", resultados.get(0).getNombre());
    }

    @Test
    void testListarTodo_Vacio() {
        // Arrange
        when(ejercicioRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Ejercicio> resultados = ejercicioService.listarTodo();

        // Assert
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }


    @Test
    void testBuscarPorId_Encontrado() {
        // Arrange
        when(ejercicioRepository.findById(1L)).thenReturn(Optional.of(ejercicio));

        // Act
        Optional<Ejercicio> resultado = ejercicioService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Press de Banca", resultado.get().getNombre());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        // Arrange
        when(ejercicioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Ejercicio> resultado = ejercicioService.buscarPorId(99L);

        // Assert
        assertFalse(resultado.isPresent());
    }
}
