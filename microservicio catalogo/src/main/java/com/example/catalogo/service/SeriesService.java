package com.example.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalogo.dto.SeriesDTO;
import com.example.catalogo.model.Series;
import com.example.catalogo.repository.SeriesRepository;

@Service

public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Series registrarSerie(SeriesDTO dto){
        Series series = new Series();

        
        series.setEjercicioId(dto.getEjercicioId());
        series.setPeso(dto.getPeso());
        series.setRepeticiones(dto.getRepeticion());

        
        return seriesRepository.save(series);
    }

    public List<Series> obtenerHistorialPorEjercicio(Long ejercicioId){
        
        return seriesRepository.findByEjercicioId(ejercicioId);
    }

    public List<Series> listarTodasLasSeries() {
        return seriesRepository.findAll();
    }

}
