package com.powerApp.pagos.repository;

import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByUsuarioId(Long usuarioId);

    List<Pago> findByMembresiaId(Long membresiaId);

    List<Pago> findByEstado(EstadoPago estado);

    List<Pago> findByMetodoPagoIgnoreCase(String metodoPago);

    List<Pago> findByReferenciaPasarelaContainingIgnoreCase(String referenciaPasarela);
}
