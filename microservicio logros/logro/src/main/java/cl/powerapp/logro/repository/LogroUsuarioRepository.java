package cl.powerapp.logro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.powerapp.logro.model.LogroUsuario;

public interface LogroUsuarioRepository
        extends JpaRepository<LogroUsuario, Long>{

    List<LogroUsuario> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndLogroId(
            Long usuarioId,
            Long logroId);
}
