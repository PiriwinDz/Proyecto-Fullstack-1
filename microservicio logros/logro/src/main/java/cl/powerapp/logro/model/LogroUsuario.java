package cl.powerapp.logro.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="logros_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogroUsuario{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "id de usuario no puede estar vacio")
    private Long usuarioId;

    @NotNull(message = "id de logro no puede estar vacio")
    private Long logroId;

    private LocalDate fechaDesbloqueo;

}
