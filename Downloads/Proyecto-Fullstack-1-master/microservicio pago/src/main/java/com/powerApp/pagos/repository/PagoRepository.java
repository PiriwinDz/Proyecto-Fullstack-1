package com.powerApp.pagos.repository;

import com.powerApp.pagos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


@Repository

public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Buscar por metodo de pago (ignorando mayuscula y minuscula)
    List<Pago> findByMetodoPagoIgnoreCase(String metodoPago);

    // Buscar por usuarioId    
    List<Pago> findByUsuarioId(Long usuarioId);


    // Buscar por estado 
    List<Pago> findByEstado(EstadoPago estado);

    // Buscar por membresiaId
    List<Pago> findByMembresiaId(Long membresiaId);

}