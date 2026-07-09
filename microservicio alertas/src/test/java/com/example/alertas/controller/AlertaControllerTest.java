package com.example.alertas.controller;

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

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.dto.AlertaResponseDTO;
import com.example.alertas.service.AlertaService;

@WebMvcTest(AlertaController.class)
public class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlertaService service;

    @Test
    void crearAlerta() throws Exception {

        String alertaJson = """
            {
                "usuarioId":1,
                "titulo":"Alerta de prueba",
                "mensaje":"Mensaje de prueba",
                "tipo":"INFO"
            }
            """;

        AlertaResponseDTO alerta = AlertaResponseDTO.builder()
                .id(1L)
                .usuarioId(1L)
                .titulo("Alerta de prueba")
                .mensaje("Mensaje de prueba")
                .tipo("INFO")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.crear(any(AlertaRequestDTO.class))).thenReturn(alerta);

        mockMvc.perform(post("/api/v1/alertas")
                .contentType(APPLICATION_JSON)
                .content(alertaJson))
                .andExpect(status().isCreated());
    }

    @Test
    void listarAlertas() throws Exception {

        List<AlertaResponseDTO> alertas = List.of(
                AlertaResponseDTO.builder()
                        .id(1L)
                        .usuarioId(1L)
                        .titulo("Alerta")
                        .mensaje("Mensaje")
                        .tipo("INFO")
                        .activa(true)
                        .creadaEn(LocalDateTime.now())
                        .build()
        );

        when(service.listar()).thenReturn(alertas);

        mockMvc.perform(get("/api/v1/alertas"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId() throws Exception {

        AlertaResponseDTO alerta = AlertaResponseDTO.builder()
                .id(1L)
                .usuarioId(1L)
                .titulo("Alerta")
                .mensaje("Mensaje")
                .tipo("INFO")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.buscarPorId(anyLong())).thenReturn(alerta);

        mockMvc.perform(get("/api/v1/alertas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarAlerta() throws Exception {

        String alertaJson = """
            {
                "usuarioId":1,
                "titulo":"Alerta actualizada",
                "mensaje":"Nuevo mensaje",
                "tipo":"ERROR"
            }
            """;

        AlertaResponseDTO alerta = AlertaResponseDTO.builder()
                .id(1L)
                .usuarioId(1L)
                .titulo("Alerta actualizada")
                .mensaje("Nuevo mensaje")
                .tipo("ERROR")
                .activa(true)
                .creadaEn(LocalDateTime.now())
                .build();

        when(service.actualizar(anyLong(), any(AlertaRequestDTO.class)))
                .thenReturn(alerta);

        mockMvc.perform(put("/api/v1/alertas/1")
                .contentType(APPLICATION_JSON)
                .content(alertaJson))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarAlerta() throws Exception {

        doNothing().when(service).eliminar(anyLong());

        mockMvc.perform(delete("/api/v1/alertas/1"))
                .andExpect(status().isNoContent());
    }

}