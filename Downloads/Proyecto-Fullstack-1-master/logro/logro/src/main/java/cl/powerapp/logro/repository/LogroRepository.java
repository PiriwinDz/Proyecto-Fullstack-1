package cl.powerapp.logro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.powerapp.logro.model.Logro;

import java.util.List;

public interface LogroRepository extends JpaRepository<Logro, Long> {

    List<Logro> findByUsuarioId(Long usuarioId);
    
}
