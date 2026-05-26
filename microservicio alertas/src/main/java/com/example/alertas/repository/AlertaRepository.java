package com.example.alertas.repository;


import com.example.alertas.model.Alerta;


import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;


@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
