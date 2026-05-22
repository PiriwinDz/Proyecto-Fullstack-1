package cl.powerapp.logro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogroListadoDTO {

    private Long id;
    private Long usuarioId;
    private String nombre;
    private String descripcion;
    private Integer puntos;
    private LocalDate fechaDesbloqueo;

    
}
