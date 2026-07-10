package com.example.catalogo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.service.EjercicioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EjercicioController.class)
class EjercicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EjercicioService ejercicioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearEjercicio() throws Exception {

        EjercicioDTO ejercicioDTO = new EjercicioDTO(
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

        when(ejercicioService.crear(any(EjercicioDTO.class)))
                .thenReturn(ejercicio);

        mockMvc.perform(post("/api/ejercicios")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejercicioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ejercicioId", is(1)))
                .andExpect(jsonPath("$.nombre", is("Press de Banca")));
    }

    @Test
    void listarEjercicios() throws Exception {

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioService.listar())
                .thenReturn(List.of(ejercicio));

        mockMvc.perform(get("/api/ejercicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Press de Banca")));
    }

    @Test
    void buscarEjercicioPorId() throws Exception {

        Ejercicio ejercicio = new Ejercicio(
                1L,
                "Press de Banca",
                "Pecho",
                "Ejercicio de pecho con barra."
        );

        when(ejercicioService.buscarPorId(anyLong()))
                .thenReturn(ejercicio);

        mockMvc.perform(get("/api/ejercicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ejercicioId", is(1)))
                .andExpect(jsonPath("$.nombre", is("Press de Banca")));
    }

    @Test
    void actualizarEjercicio() throws Exception {

        EjercicioDTO ejercicioDTO = new EjercicioDTO(
                "Press Inclinado",
                "Pecho",
                "Actualizado"
        );

        Ejercicio ejercicioActualizado = new Ejercicio(
                1L,
                "Press Inclinado",
                "Pecho",
                "Actualizado"
        );

        when(ejercicioService.actualizar(anyLong(), any(EjercicioDTO.class)))
                .thenReturn(ejercicioActualizado);

        mockMvc.perform(put("/api/ejercicios/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejercicioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Press Inclinado")));
    }

    @Test
    void eliminarEjercicio() throws Exception {

        doNothing().when(ejercicioService).eliminar(anyLong());

        mockMvc.perform(delete("/api/ejercicios/1"))
                .andExpect(status().isNoContent());
    }

}