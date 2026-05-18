package com.clientes.personas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.personas.model.Pago;
import com.clientes.personas.model.Persona;
import com.clientes.personas.service.PersonaService;

import com.clientes.personas.dto.PersonaSimpleDTO;



import com.clientes.personas.dto.PersonaListadoDTO;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    // POST: agregar persona
    @PostMapping("/agregar")
    public Persona crearPersona(@RequestBody Persona persona) {
        return service.guardarPersona(persona);
    }

    @GetMapping("/{id}/pagos")
    public List<Pago> obtenerPagosPorPersona(@PathVariable Integer id) {
        return service.obtenerPagosPorPersona(id);
    }

    // GET: listar personas usando DTO (respuesta simplificada)
    @GetMapping("/listar-dto")
    public List<PersonaListadoDTO> listarDTO() {
        return service.listarDTO();
    }

    // GET: version simplificada 
    @GetMapping("/{id}/detalle-simple")
    public PersonaSimpleDTO obtenerDetalleSimple(@PathVariable Integer id) {
        return service.obtenerDetalleSimple(id);
    }


}
