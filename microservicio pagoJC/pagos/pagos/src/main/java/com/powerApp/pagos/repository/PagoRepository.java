package com.powerApp.pagos.repository;

import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Buscar pagos por usuario
    List<Pago> findByUsuarioId(Long usuarioId);

    // Buscar pagos por membresía
    List<Pago> findByMembresiaId(Long membresiaId);

    // Buscar pagos por estado (PENDIENTE, PROCESADO, FALLIDO)
    List<Pago> findByEstado(EstadoPago estado);

    // Buscar pagos por método de pago (tarjeta, transferencia, etc.)
    List<Pago> findByMetodoPagoIgnoreCase(String metodoPago);

    // Buscar pagos por referencia de pasarela
    List<Pago> findByReferenciaPasarelaContainingIgnoreCase(String referenciaPasarela);
}
