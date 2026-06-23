package com.example.catalogo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.service.EjercicioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EjercicioController.class)
class EjercicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EjercicioService ejercicioService;

    @Autowired
    private ObjectMapper objectMapper;

    private EjercicioDTO ejercicioDTO;
    private Ejercicio ejercicio;

    @BeforeEach
    void setUp() {
        ejercicioDTO = new EjercicioDTO("Press de Banca", "Pecho", "Ejercicio de pecho con barra.");
        ejercicio = new Ejercicio(1L, "Press de Banca", "Pecho", "Ejercicio de pecho con barra.");
    }

    @Test
    void testCrearEjercicio() throws Exception {
        
        when(ejercicioService.guardarEjercicio(any(EjercicioDTO.class))).thenReturn(ejercicio);

        
        mockMvc.perform(post("/api/ejercicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejercicioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Press de Banca")));
    }

    @Test
    void testListarTodo() throws Exception {
        
        when(ejercicioService.listarTodo()).thenReturn(List.of(ejercicio));

       
        mockMvc.perform(get("/api/ejercicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Press de Banca")));
    }

    @Test
    void testBuscarPorId_Encontrado() throws Exception {
        
        when(ejercicioService.buscarPorId(1L)).thenReturn(Optional.of(ejercicio));

        
        mockMvc.perform(get("/api/ejercicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Press de Banca")));
    }

    @Test
    void testBuscarPorId_NoEncontrado() throws Exception {
        
        when(ejercicioService.buscarPorId(99L)).thenReturn(Optional.empty());

        
        mockMvc.perform(get("/api/ejercicios/99"))
                .andExpect(status().isNotFound());
    }
}
