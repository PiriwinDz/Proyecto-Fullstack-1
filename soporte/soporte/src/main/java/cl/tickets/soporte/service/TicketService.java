package cl.tickets.soporte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.tickets.soporte.dto.TicketListadoDTO;
import cl.tickets.soporte.dto.TicketSimpleDTO;
import cl.tickets.soporte.dto.UsuarioTicketsDTO;
import cl.tickets.soporte.model.TicketSoporte;
import cl.tickets.soporte.model.Usuario;
import cl.tickets.soporte.repository.TicketRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    public TicketSoporte guardar(Long usuarioId, TicketSoporte ticket){

        Usuario usuario = obtenerUsuario(usuarioId);

        ticket.setUsuarioId(usuario.getId());
        
        ticket.setEstado("ABIERTO");

        ticket.setFechaCreacion(LocalDate.now());

        return repository.save(ticket);
    }

    public List<TicketListadoDTO> listarDTO(){

        List<TicketSoporte> tickets = repository.findAll();

        List<TicketListadoDTO> resultado = new ArrayList<>();

        for(TicketSoporte ticket : tickets){

            TicketListadoDTO dto = convertirListadoDTO(ticket);

            resultado.add(dto);
        }

        return resultado;
    }

    public TicketSimpleDTO obtenerDetalleSimple(
            Long id){

        TicketSoporte ticket =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket no encontrado"));

        return convertirSimpleDTO(ticket);
    }

    public List<TicketListadoDTO> obtenerTicketsUsuario(
            Long usuarioId){

        List<TicketSoporte> tickets = repository.findByUsuarioId(usuarioId);

        List<TicketListadoDTO> resultado = new ArrayList<>();

        for(TicketSoporte ticket : tickets){

            TicketListadoDTO dto = convertirListadoDTO(ticket);

            resultado.add(dto);
        }

        return resultado;
    }

    public Usuario obtenerUsuario(Long usuarioId){

        RestTemplate restTemplate =
                new RestTemplate();

        String url ="http://localhost:8089/api/auth/usuarios/"+ usuarioId;

        return restTemplate.getForObject(url,Usuario.class);

    }

    public UsuarioTicketsDTO obtenerTicketsConUsuario(
            Long usuarioId){

        Usuario usuario = obtenerUsuario(usuarioId);

        List<TicketListadoDTO> tickets = obtenerTicketsUsuario(usuarioId);

        UsuarioTicketsDTO respuesta = new UsuarioTicketsDTO();

                respuesta.setNombreUsuario(usuario.getNombre());

        respuesta.setTickets(tickets);

        return respuesta;
    }

    public TicketSoporte actualizar(Long id,TicketSoporte ticket){

        TicketSoporte existente =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket no encontrado"));

        existente.setTitulo(ticket.getTitulo());

        existente.setDescripcion(ticket.getDescripcion());

        existente.setEstado(ticket.getEstado());

        return repository.save(existente);
    }

    public void eliminar(Long id){

        TicketSoporte ticket =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket no encontrado"));

        repository.delete(ticket);
    }

    public TicketSimpleDTO convertirSimpleDTO(
            TicketSoporte ticket){

        return new TicketSimpleDTO(
                ticket.getId(),
                ticket.getTitulo()
        );
    }

    public TicketListadoDTO convertirListadoDTO(
            TicketSoporte ticket){

        return new TicketListadoDTO(
                ticket.getId(),
                ticket.getTitulo(),
                ticket.getDescripcion(),
                ticket.getEstado(),
                ticket.getFechaCreacion()
        );
    }
}


