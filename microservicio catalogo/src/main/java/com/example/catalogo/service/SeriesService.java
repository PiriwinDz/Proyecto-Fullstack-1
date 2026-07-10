package com.example.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalogo.dto.SeriesDTO;
import com.example.catalogo.exception.SeriesNoEncontradaException;
import com.example.catalogo.model.Series;
import com.example.catalogo.repository.SeriesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public Series crear(SeriesDTO dto) {

        Series series = new Series();

        series.setEjercicioId(dto.getEjercicioId());
        series.setPeso(dto.getPeso());
        series.setRepeticiones(dto.getRepeticion());

        return seriesRepository.save(series);
    }

    public List<Series> listar() {
        return seriesRepository.findAll();
    }

    public Series buscarPorId(Long id) {
        return obtenerSerie(id);
    }

    public List<Series> obtenerHistorialPorEjercicio(Long ejercicioId) {
        return seriesRepository.findByEjercicioId(ejercicioId);
    }

    public Series actualizar(Long id, SeriesDTO dto) {

        Series series = obtenerSerie(id);

        series.setEjercicioId(dto.getEjercicioId());
        series.setPeso(dto.getPeso());
        series.setRepeticiones(dto.getRepeticion());

        return seriesRepository.save(series);
    }

    public void eliminar(Long id) {

        Series series = obtenerSerie(id);

        seriesRepository.delete(series);
    }

    private Series obtenerSerie(Long id) {

        return seriesRepository.findById(id)
                .orElseThrow(() ->
                        new SeriesNoEncontradaException(id));
    }

}
