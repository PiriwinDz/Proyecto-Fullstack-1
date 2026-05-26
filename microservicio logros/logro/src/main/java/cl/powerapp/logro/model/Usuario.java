package cl.powerapp.logro.model;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Usuario {

    private Long id;

    private String nombre;
    
    private String correo;

    private String rol; 

    private Boolean activo; 
    
    private LocalDateTime creadoEn;
    
}
