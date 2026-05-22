package com.clientes.personas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clientes.personas.dto.PersonaListadoDTO;
import com.clientes.personas.dto.PersonaSimpleDTO;
import com.clientes.personas.model.Pago;
import com.clientes.personas.model.Persona;
import com.clientes.personas.service.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    // POST: agregar persona
    @PostMapping("/agregar")
    public ResponseEntity<Persona> crearPersona(@Valid @RequestBody Persona persona) {

        Persona nueva = service.guardarPersona(persona);

        return ResponseEntity.status(201).body(nueva);
    }

    // GET: obtener pagos de una persona
    @GetMapping("/{id}/pagos")
    public List<Pago> obtenerPagosPorPersona(@PathVariable Integer id) {

        return service.obtenerPagosPorPersona(id);
    }

    // GET: listar personas con DTO
    @GetMapping("/listar-dto")
    public List<PersonaListadoDTO> listarDTO() {

        return service.listarDTO();
    }

    // GET: detalle simple de persona
    @GetMapping("/{id}/detalle-simple")
    public PersonaSimpleDTO obtenerDetalleSimple(@PathVariable Integer id) {

        return service.obtenerDetalleSimple(id);
    }
}
