package com.powerApp.pagos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.service.PagoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PagoService pagoService;

        @Autowired
        private ObjectMapper objectMapper;

        private Pago pago;

        @BeforeEach
        void setUp() {
                pago = Pago.builder()
                                .id(1L)
                                .usuarioId(10L)
                                .membresiaId(20L)
                                .monto(BigDecimal.valueOf(99.99))
                                .estado(EstadoPago.PENDIENTE)
                                .metodoPago("Tarjeta")
                                .referenciaPasarela("REF123")
                                .build();
        }

        @Test
        void testListarPagos() throws Exception {
                Mockito.when(pagoService.listar()).thenReturn(Arrays.asList(pago));

                mockMvc.perform(get("/pagos"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(pago.getId()))
                                .andExpect(jsonPath("$[0].metodoPago").value(pago.getMetodoPago()));
        }

        @Test
        void testBuscarPorId() throws Exception {
                Mockito.when(pagoService.buscarPorId(1L)).thenReturn(Optional.of(pago));

                mockMvc.perform(get("/pagos/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(pago.getId()))
                                .andExpect(jsonPath("$.metodoPago").value(pago.getMetodoPago()));
        }

        @Test
        void testCrearPago() throws Exception {
                PagoRequestDTO requestDTO = PagoRequestDTO.builder()
                                .usuarioId(10L)
                                .membresiaId(20L)
                                .monto(BigDecimal.valueOf(99.99))
                                .metodoPago("Tarjeta")
                                .referenciaPasarela("REF123")
                                .build();

                Mockito.when(pagoService.crearPago(Mockito.any(PagoRequestDTO.class)))
                                .thenReturn(PagoResponseDTO.builder()
                                                .id(1L)
                                                .usuarioId(10L)
                                                .membresiaId(20L)
                                                .monto(BigDecimal.valueOf(99.99))
                                                .estado("PENDIENTE")
                                                .metodoPago("Tarjeta")
                                                .referenciaPasarela("REF123")
                                                .mensaje("Pago creado correctamente")
                                                .build());

                mockMvc.perform(post("/pagos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.mensaje").value("Pago creado correctamente"));
        }

        @Test
        void testActualizarPago() throws Exception {
                Pago nuevoPago = Pago.builder()
                                .id(1L)
                                .usuarioId(10L)
                                .membresiaId(20L)
                                .monto(BigDecimal.valueOf(120.00))
                                .estado(EstadoPago.APROBADO)
                                .metodoPago("Tarjeta")
                                .referenciaPasarela("REF999")
                                .build();

                Mockito.when(pagoService.actualizar(Mockito.eq(1L), Mockito.any(Pago.class)))
                                .thenReturn(nuevoPago);

                mockMvc.perform(put("/pagos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(nuevoPago)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.monto").value(120.00))
                                .andExpect(jsonPath("$.estado").value("APROBADO"))
                                .andExpect(jsonPath("$.referenciaPasarela").value("REF999"));
        }

        @Test
        void testEliminarPago() throws Exception {
                Mockito.doNothing().when(pagoService).eliminar(1L);

                mockMvc.perform(delete("/pagos/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Pago eliminado correctamente"));
        }
}
