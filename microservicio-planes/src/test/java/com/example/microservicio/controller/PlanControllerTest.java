package com.example.microservicio.controller;

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

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.microservicio.dto.PlanRequestDTO;
import com.example.microservicio.dto.PlanResponseDTO;
import com.example.microservicio.service.PlanService;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlanService service;

    @Test
    void testCreatePlan() throws Exception {
        String planJson = """
            {
                "nombre":"Plan Básico",
                "descripcion":"Plan de entrenamiento para principiantes",
                "precio": 29.99
            }
            """;

        PlanResponseDTO plan = PlanResponseDTO.builder()
                .id(1L)
                .nombre("Plan Básico")
                .descripcion("Plan de entrenamiento para principiantes")
                .coste(29.99)
                .build();

        when(service.createPlan(any(PlanRequestDTO.class))).thenReturn(plan);

        mockMvc.perform(post("/api/planes")
                .contentType(APPLICATION_JSON)
                .content(planJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllPlanes() throws Exception {
        List<PlanResponseDTO> planes = List.of(
            PlanResponseDTO.builder()
                .id(1L)
                .nombre("Plan Básico")
                .descripcion("Plan de entrenamiento para principiantes")
                .coste(29.99)
                .build()
        );

        when(service.getAllPlanes()).thenReturn(planes);

        mockMvc.perform(get("/api/planes"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPlanById() throws Exception {
        PlanResponseDTO plan = PlanResponseDTO.builder()
                .id(1L)
                .nombre("Plan Básico")
                .descripcion("Plan de entrenamiento para principiantes")
                .coste(29.99)
                .build();

        when(service.getPlanById(anyLong())).thenReturn(plan);

        mockMvc.perform(get("/api/planes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePlan() throws Exception {
        String planJson = """
            {
                "nombre":"Plan Premium",
                "descripcion":"Plan de entrenamiento avanzado",
                "precio": 49.99
            }
            """;

        PlanResponseDTO plan = PlanResponseDTO.builder()
                .id(1L)
                .nombre("Plan Premium")
                .descripcion("Plan de entrenamiento avanzado")
                .coste(49.99)
                .build();

        when(service.updatePlan(anyLong(), any(PlanRequestDTO.class))).thenReturn(plan);

        mockMvc.perform(put("/api/planes/1")
                .contentType(APPLICATION_JSON)
                .content(planJson))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePlan() throws Exception {
        doNothing().when(service).deletePlan(anyLong());

        mockMvc.perform(delete("/api/planes/1"))
                .andExpect(status().isNoContent());
    }
}
