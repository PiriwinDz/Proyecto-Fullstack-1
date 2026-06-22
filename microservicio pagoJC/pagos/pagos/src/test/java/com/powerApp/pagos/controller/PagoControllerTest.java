package com.powerApp.pagos.controller;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.service.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    // ✅ Test GET: listar pagos
    @Test
    void listarPagos() throws Exception {
        Pago pago = Pago.builder()
                .id(1L)
                .usuarioId(101L)
                .membresiaId(202L)
                .monto(new BigDecimal("5000.00"))
                .estado(EstadoPago.APROBADO)
                .metodoPago("TARJETA")
                .referenciaPasarela("REF123")
                .creadoEn(LocalDateTime.now())
                .procesadoEn(LocalDateTime.now())
                .build();

        List<Pago> pagos = List.of(pago);

        when(pagoService.listar()).thenReturn(pagos);

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk());
    }

    // ✅ Test POST: crear pago
    @Test
    void crearPago() throws Exception {
        String pagoJson = """
                {
                    "usuarioId": 101,
                    "membresiaId": 202,
                    "monto": 5000.00,
                    "metodoPago": "TARJETA",
                    "referenciaPasarela": "REF123"
                }
                """;

        PagoResponseDTO pagoCreado = new PagoResponseDTO(
                1L,
                101L,
                202L,
                new BigDecimal("5000.00"),
                "APROBADO",
                "TARJETA",
                "REF123");

        when(pagoService.crearPago(any(PagoRequestDTO.class))).thenReturn(pagoCreado);

        mockMvc.perform(post("/pagos")
                .contentType(APPLICATION_JSON)
                .content(pagoJson))
                .andExpect(status().isCreated());
    }
}
