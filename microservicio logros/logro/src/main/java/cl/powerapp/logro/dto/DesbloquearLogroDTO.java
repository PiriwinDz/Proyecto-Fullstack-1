package cl.powerapp.logro.dto;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesbloquearLogroDTO{

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;
    @NotNull(message = "El logroId es obligatorio")
    private Long logroId;

}
