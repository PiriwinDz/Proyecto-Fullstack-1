package cl.powerapp.logro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.powerapp.logro.model.Logro;


public interface LogroRepository extends JpaRepository<Logro, Long> {

    
}
