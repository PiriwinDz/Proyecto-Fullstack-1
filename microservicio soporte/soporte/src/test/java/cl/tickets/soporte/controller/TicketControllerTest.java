package cl.tickets.soporte.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.tickets.soporte.dto.TicketListadoDTO;
import cl.tickets.soporte.dto.TicketSimpleDTO;
import cl.tickets.soporte.dto.UsuarioTicketsDTO;
import cl.tickets.soporte.model.TicketSoporte;
import cl.tickets.soporte.service.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService service;

    @Test
    void crearTicket() throws Exception {

        String ticketJson = """
            {
                "titulo":"Problema de pago",
                "descripcion":"No puedo completar la compra"
            }
                """;

        TicketSoporte ticketGuardado = new TicketSoporte(
            1L,
            1L,
            "Problema de pago",
            "No puedo completar la compra",
            "ABIERTO",
            LocalDate.now()
        );

        when(service.guardar(anyLong(), any(TicketSoporte.class))).thenReturn(ticketGuardado);
        mockMvc.perform(post("/tickets/usuario/1/agregar")
               .contentType(APPLICATION_JSON)
               .content(ticketJson))
               .andExpect(status().isCreated());
    }

    @Test
    void listarTickets() throws Exception {

        List<TicketListadoDTO> tickets = List.of(
            new TicketListadoDTO(
                    1L,
                    "Problema de pago",
                    "No puedo completar la compra",
                    "ABIERTO",
                    LocalDate.now()
            )
        );

        when(service.listarDTO()).thenReturn(tickets);
        mockMvc.perform(get("/tickets/listar"))
               .andExpect(status().isOk());
    }

    @Test
    void obtenerDetalleSimple() throws Exception {

        TicketSimpleDTO dto = new TicketSimpleDTO(
            1L,
            "Problema de pago"
        );

        when(service.obtenerDetalleSimple(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/tickets/1/detalle-simple"))
               .andExpect(status().isOk());
    }

    @Test
    void obtenerTicketsUsuario() throws Exception {

        UsuarioTicketsDTO dto = new UsuarioTicketsDTO();

        dto.setNombreUsuario("Juan");

        dto.setTickets(List.of(
                new TicketListadoDTO(
                    1L,
                    "Problema de pago",
                    "No puedo completar la compra",
                    "ABIERTO",
                    LocalDate.now()
                )
        ));

        when(service.obtenerTicketsConUsuario(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/tickets/usuario/1"))
               .andExpect(status().isOk());
    }

    @Test
    void actualizarTicket() throws Exception {

        String ticketJson = """
            {
                "titulo":"Problema actualizado",
                "descripcion":"Nueva descripcion",
                "estado":"CERRADO"
            }
                """;

        TicketSoporte actualizado = new TicketSoporte(
            1L,
            1L,
            "Problema actualizado",
            "Nueva descripcion",
            "CERRADO",
            LocalDate.now()
        );

        when(service.actualizar(anyLong(),any(TicketSoporte.class))).thenReturn(actualizado);

        mockMvc.perform(put("/tickets/actualizar/1")
               .contentType(APPLICATION_JSON)
               .content(ticketJson))
               .andExpect(status().isOk());
    }

    @Test
    void eliminarTicket() throws Exception {

        mockMvc.perform(delete("/tickets/eliminar/1"))
               .andExpect(status().isNoContent());
    }

}
