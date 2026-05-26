package cl.tickets.soporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketListadoDTO {

    private Long id;

    private String titulo;

    private String descripcion;

    private String estado;

    private LocalDate fechaCreacion;
}

