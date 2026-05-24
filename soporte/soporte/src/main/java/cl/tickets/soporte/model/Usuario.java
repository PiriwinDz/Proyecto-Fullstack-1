package cl.tickets.soporte.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Usuario {

    private Long id;

    private String nombre;

    private String correo;

    private String rol;

    private Boolean activo;

    private LocalDateTime creadoEn;
}


