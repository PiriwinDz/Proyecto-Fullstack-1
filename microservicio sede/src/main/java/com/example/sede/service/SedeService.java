package com.example.sede.service;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.exception.SedeNoEncontradaException;
import com.example.sede.model.Sede;
import com.example.sede.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SedeService {

    private final SedeRepository sedeRepository;

    public List<SedeResponseDTO> listar() {

        List<Sede> sedes = sedeRepository.findByActivoTrue();
        List<SedeResponseDTO> respuesta = new ArrayList<>();

        for (Sede sede : sedes) {
            respuesta.add(convertirADTO(sede));
        }

        return respuesta;
    }

    public SedeResponseDTO buscarPorId(Long id) {
        return convertirADTO(buscarSedePorId(id));
    }

    public SedeResponseDTO crear(SedeRequestDTO dto) {

        Sede sede = Sede.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .horario(dto.getHorario())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .ocupacionActual(0)
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        return convertirADTO(sedeRepository.save(sede));
    }

    public SedeResponseDTO actualizar(Long id, SedeRequestDTO dto) {

        Sede sede = buscarSedePorId(id);

        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setHorario(dto.getHorario());
        sede.setCapacidadMaxima(dto.getCapacidadMaxima());

        return convertirADTO(sedeRepository.save(sede));
    }

    public SedeResponseDTO registrarEntrada(Long id) {

        Sede sede = buscarSedePorId(id);

        if (sede.getOcupacionActual() >= sede.getCapacidadMaxima()) {
            throw new IllegalArgumentException(
                    "La sede alcanzó su capacidad máxima.");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() + 1);

        return convertirADTO(sedeRepository.save(sede));
    }

    public SedeResponseDTO registrarSalida(Long id) {

        Sede sede = buscarSedePorId(id);

        if (sede.getOcupacionActual() <= 0) {
            throw new IllegalArgumentException(
                    "La ocupación actual ya es cero.");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() - 1);

        return convertirADTO(sedeRepository.save(sede));
    }

    public void eliminar(Long id) {

        Sede sede = buscarSedePorId(id);

        sedeRepository.delete(sede);
    }

    private Sede buscarSedePorId(Long id) {

        return sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));
    }

    private SedeResponseDTO convertirADTO(Sede sede) {

        int porcentaje = 0;

        if (sede.getCapacidadMaxima() > 0) {
            porcentaje = (sede.getOcupacionActual() * 100)
                    / sede.getCapacidadMaxima();
        }

        return SedeResponseDTO.builder()
                .id(sede.getId())
                .nombre(sede.getNombre())
                .direccion(sede.getDireccion())
                .horario(sede.getHorario())
                .capacidadMaxima(sede.getCapacidadMaxima())
                .ocupacionActual(sede.getOcupacionActual())
                .porcentajeOcupacion(porcentaje)
                .activo(sede.getActivo())
                .creadoEn(sede.getCreadoEn())
                .build();
    }
}