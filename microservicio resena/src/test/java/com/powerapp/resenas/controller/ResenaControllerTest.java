package com.powerapp.resenas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerapp.resenas.dto.ResenaRequestDTO;
import com.powerapp.resenas.dto.ResenaResponseDTO;
import com.powerapp.resenas.model.Resena;
import com.powerapp.resenas.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Resena resena;

    @BeforeEach
    void setUp() {
        resena = Resena.builder()
                .id(1L)
                .usuarioId(10L)
                .ejercicioId(20L)
                .calificacion(5)
                .comentario("Excelente ejercicio")
                .build();
    }

    @Test
    void testListarResenas() throws Exception {
        Mockito.when(resenaService.listar()).thenReturn(Arrays.asList(resena));

        mockMvc.perform(get("/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(resena.getId()))
                .andExpect(jsonPath("$[0].comentario").value(resena.getComentario()));
    }

    @Test
    void testBuscarPorId() throws Exception {
        Mockito.when(resenaService.buscarPorId(1L)).thenReturn(Optional.of(resena));

        mockMvc.perform(get("/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resena.getId()))
                .andExpect(jsonPath("$.comentario").value(resena.getComentario()));
    }

    @Test
    void testCrearResena() throws Exception {
        ResenaRequestDTO requestDTO = new ResenaRequestDTO();
        requestDTO.setUsuarioId(10L);
        requestDTO.setEjercicioId(20L);
        requestDTO.setCalificacion(5);
        requestDTO.setComentario("Muy buen ejercicio");

        Mockito.when(resenaService.crearResena(Mockito.any(ResenaRequestDTO.class)))
                .thenReturn(new ResenaResponseDTO(1L, "Reseña creada correctamente"));

        mockMvc.perform(post("/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.mensaje").value("Reseña creada correctamente"));
    }

    @Test
    void testActualizarResena() throws Exception {
        Resena nuevaResena = Resena.builder()
                .id(1L)
                .usuarioId(10L)
                .ejercicioId(20L)
                .calificacion(4)
                .comentario("Comentario actualizado")
                .build();

        Mockito.when(resenaService.actualizar(Mockito.eq(1L), Mockito.any(Resena.class)))
                .thenReturn(nuevaResena);

        mockMvc.perform(put("/resenas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaResena)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calificacion").value(4))
                .andExpect(jsonPath("$.comentario").value("Comentario actualizado"));
    }

    @Test
    void testEliminarResena() throws Exception {
        Mockito.doNothing().when(resenaService).eliminar(1L);

        mockMvc.perform(delete("/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reseña eliminada correctamente"));
    }
}
