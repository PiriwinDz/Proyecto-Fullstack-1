package com.clientes.personas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clientes.personas.dto.PersonaListadoDTO;
import com.clientes.personas.dto.PersonaSimpleDTO;
import com.clientes.personas.model.Pago;
import com.clientes.personas.model.Persona;
import com.clientes.personas.repository.PersonaRepository;

@Service
public class PersonaService {

    private final PersonaRepository repository;
    private final RestTemplate restTemplate;

    public PersonaService(PersonaRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // Guardar persona
    public Persona guardarPersona(Persona persona) {
        return repository.save(persona);
    }

    // Obtener pagos de una persona
    public List<Pago> obtenerPagosPorPersona(Integer personaId) {

        Optional<Persona> personaOpt = repository.findById(personaId);

        if (personaOpt.isEmpty()) {
            return new ArrayList<>();
        }

        Persona persona = personaOpt.get();

        List<Pago> pagos = new ArrayList<>();

        // Evitar null
        if (persona.getPagosIds() == null || persona.getPagosIds().isEmpty()) {
            return pagos;
        }

        // Buscar pagos en microservicio pago
        for (Integer pagoId : persona.getPagosIds()) {

            try {

                String url = "http://localhost:8080/pagos/" + pagoId;

                Pago pago = restTemplate.getForObject(url, Pago.class);

                if (pago != null) {
                    pagos.add(pago);
                }

            } catch (Exception e) {

                System.out.println("Error al obtener pago ID: " + pagoId);
                System.out.println("Detalle: " + e.getMessage());

            }
        }

        return pagos;
    }

    // Listar personas DTO
    public List<PersonaListadoDTO> listarDTO() {

        List<Persona> personas = repository.findAll();
        List<PersonaListadoDTO> lista = new ArrayList<>();

        for (Persona p : personas) {

            PersonaListadoDTO dto = new PersonaListadoDTO();

            dto.setNombre(p.getNombre());
            dto.setEmail(p.getEmail());

            lista.add(dto);
        }

        return lista;
    }

    // Obtener detalle simple
    public PersonaSimpleDTO obtenerDetalleSimple(Integer id) {

        Optional<Persona> personaOpt = repository.findById(id);

        if (personaOpt.isEmpty()) {
            return null;
        }

        Persona persona = personaOpt.get();

        List<Pago> pagos = obtenerPagosPorPersona(id);

        // Guardar info simple de pagos
        List<String> pagosInfo = new ArrayList<>();

        for (Pago p : pagos) {

            pagosInfo.add("Pago ID: " + p.getId());

        }

        PersonaSimpleDTO dto = new PersonaSimpleDTO();

        dto.setNombre(persona.getNombre());
        dto.setEmail(persona.getEmail());

        // Debes tener List<String> pagos en el DTO
        dto.setPagos(pagosInfo);

        return dto;
    }
}