package cl.tickets.soporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTicketsDTO {

    private String nombreUsuario;

    private List<TicketListadoDTO> tickets;
}


