package cl.powerapp.logro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesbloquearLogroDTO{

    private Long usuarioId;
    private Long logroId;

}
