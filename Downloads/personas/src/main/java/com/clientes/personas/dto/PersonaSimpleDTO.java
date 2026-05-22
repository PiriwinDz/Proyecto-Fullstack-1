package com.clientes.personas.dto;

import java.util.List;

import lombok.Data;

@Data
public class PersonaSimpleDTO {

    private String nombre;
    private String email;

    private List<String> pagos;

}
