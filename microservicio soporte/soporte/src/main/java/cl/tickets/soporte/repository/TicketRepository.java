package cl.tickets.soporte.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cl.tickets.soporte.model.TicketSoporte;

import java.util.List;

public interface TicketRepository
        extends JpaRepository<TicketSoporte, Long> {

    List<TicketSoporte> findByUsuarioId(Long usuarioId);
}
