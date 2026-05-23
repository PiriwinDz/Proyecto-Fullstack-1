package com.example.alertas.repository;

// entidad alerta
import com.example.alertas.model.Alerta;

// jpa repository
import org.springframework.data.jpa.repository.JpaRepository;

// indica que es repository
import org.springframework.stereotype.Repository;

// repository para acceso a datos
@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
