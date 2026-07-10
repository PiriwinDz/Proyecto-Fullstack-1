package com.example.sede.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.service.SedeService;

@WebMvcTest(SedeController.class)
public class SedeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SedeService service;

    @Test
    void listarSedes() throws Exception {

        List<SedeResponseDTO> sedes = List.of(
                SedeResponseDTO.builder()
                        .id(1L)
                        .nombre("Sede Central")
                        .direccion("Santiago")
                        .horario("08:00-22:00")
                        .capacidadMaxima(100)
                        .ocupacionActual(20)
                        .porcentajeOcupacion(20)
                        .activo(true)
                        .creadoEn(LocalDateTime.now())
                        .build());

        when(service.listar()).thenReturn(sedes);

        mockMvc.perform(get("/api/sedes"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId() throws Exception {

        SedeResponseDTO sede = SedeResponseDTO.builder()
                .id(1L)
                .nombre("Sede Central")
                .build();

        when(service.buscarPorId(anyLong())).thenReturn(sede);

        mockMvc.perform(get("/api/sedes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearSede() throws Exception {

        String sedeJson = """
                {
                    "nombre":"Sede Central",
                    "direccion":"Santiago",
                    "horario":"08:00-22:00",
                    "capacidadMaxima":100
                }
                """;

        SedeResponseDTO sede = SedeResponseDTO.builder()
                .id(1L)
                .nombre("Sede Central")
                .build();

        when(service.crear(any(SedeRequestDTO.class))).thenReturn(sede);

        mockMvc.perform(post("/api/sedes")
                .contentType(APPLICATION_JSON)
                .content(sedeJson))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarSede() throws Exception {

        String sedeJson = """
                {
                    "nombre":"Sede Actualizada",
                    "direccion":"Providencia",
                    "horario":"09:00-21:00",
                    "capacidadMaxima":150
                }
                """;

        SedeResponseDTO sede = SedeResponseDTO.builder()
                .id(1L)
                .nombre("Sede Actualizada")
                .build();

        when(service.actualizar(anyLong(), any(SedeRequestDTO.class)))
                .thenReturn(sede);

        mockMvc.perform(put("/api/sedes/1")
                .contentType(APPLICATION_JSON)
                .content(sedeJson))
                .andExpect(status().isOk());
    }

    @Test
    void registrarEntrada() throws Exception {

        SedeResponseDTO sede = SedeResponseDTO.builder()
                .id(1L)
                .ocupacionActual(11)
                .build();

        when(service.registrarEntrada(anyLong()))
                .thenReturn(sede);

        mockMvc.perform(put("/api/sedes/1/entrada"))
                .andExpect(status().isOk());
    }

    @Test
    void registrarSalida() throws Exception {

        SedeResponseDTO sede = SedeResponseDTO.builder()
                .id(1L)
                .ocupacionActual(9)
                .build();

        when(service.registrarSalida(anyLong()))
                .thenReturn(sede);

        mockMvc.perform(put("/api/sedes/1/salida"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarSede() throws Exception {

        doNothing().when(service).eliminar(anyLong());

        mockMvc.perform(delete("/api/sedes/1"))
                .andExpect(status().isNoContent());
    }
}