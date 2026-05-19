package cl.powerapp.logros.repository;

import cl.powerapp.logros.model.Logro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface LogroRepository extends JpaRepository<Logro,Integer>{

    List<Logro> findByUsuarioId(Long usuarioId);

 
}
