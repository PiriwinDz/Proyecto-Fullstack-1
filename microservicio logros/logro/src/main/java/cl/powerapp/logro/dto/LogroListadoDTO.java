package cl.powerapp.logro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogroListadoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer puntos;
       
}
