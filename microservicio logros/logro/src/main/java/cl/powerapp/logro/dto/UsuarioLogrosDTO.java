package cl.powerapp.logro.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogrosDTO {

    private String nombreUsuario;
    private List<LogroListadoDTO> logros;

}
