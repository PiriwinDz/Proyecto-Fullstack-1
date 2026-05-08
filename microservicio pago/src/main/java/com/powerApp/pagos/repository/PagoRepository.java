package com.powerApp.pagos.repository;

import com.powerApp.pagos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByUsuarioId(Long usuarioId);
    List<Pago> findByEstado(EstadoPago estado);

}