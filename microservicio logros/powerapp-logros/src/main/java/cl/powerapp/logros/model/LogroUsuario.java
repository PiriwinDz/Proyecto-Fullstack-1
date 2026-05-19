package cl.powerapp.logros.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LogroUsuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer usuarioId;

    private Integer logroId;

    private Boolean desbloqueado;

    private String fechaDesbloqueo;

}
