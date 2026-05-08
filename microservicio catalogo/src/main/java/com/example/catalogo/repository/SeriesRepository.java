package com.example.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.catalogo.model.Series;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Int> {

    List<Series>FindByEjercicioId(int EjercicioId);
    List<Series>FindAllByOrderByFechaHoraDesc();

}
