package com.example.alertas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.dto.AlertaResponseDTO;
import com.example.alertas.service.AlertaService;

@WebMvcTest(AlertaController.class)
class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlertaService service;

    @Test
    void listarAlertas() throws Exception {

        List<AlertaResponseDTO> alertas = List.of(
                AlertaResponseDTO.builder()
                        .id(1L)
                        .titulo("Alerta Test")
                        .mensaje("Mensaje Test")
                        .tipo("INFO")
                        .activa(true)
                        .creadaEn(LocalDateTime.now())
                        .build());

        when(service.listar()).thenReturn(alertas);

        mockMvc.perform(get("/api/v1/alertas"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId() throws Exception {

        AlertaResponseDTO alerta = AlertaResponseDTO.builder()
                .id(1L)
                .titulo("Alerta Test")
                .mensaje("Mensaje Test")
                .tipo("INFO")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.buscarPorId(anyLong())).thenReturn(alerta);

        mockMvc.perform(get("/api/v1/alertas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearAlerta() throws Exception {

        String alertaJson = """
                {
                    "titulo":"Nueva alerta",
                    "mensaje":"Mensaje de prueba",
                    "tipo":"INFO"
                }
                """;

        AlertaResponseDTO respuesta = AlertaResponseDTO.builder()
                .id(1L)
                .titulo("Nueva alerta")
                .mensaje("Mensaje de prueba")
                .tipo("INFO")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.crear(any(AlertaRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/alertas")
                .contentType(APPLICATION_JSON)
                .content(alertaJson))
                .andExpect(status().isCreated());
    }

    @Test
    void desactivarAlerta() throws Exception {

        AlertaResponseDTO alerta = AlertaResponseDTO.builder()
                .id(1L)
                .titulo("Alerta Test")
                .mensaje("Mensaje Test")
                .tipo("INFO")
                .activa(false)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.desactivar(anyLong())).thenReturn(alerta);

        mockMvc.perform(put("/api/v1/alertas/1/desactivar"))
                .andExpect(status().isOk());
    }
}
