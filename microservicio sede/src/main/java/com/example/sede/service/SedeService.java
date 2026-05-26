package com.example.sede.service;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.exception.SedeNoEncontradaException;
import com.example.sede.model.Sede;
import com.example.sede.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class SedeService {

    private final SedeRepository sedeRepository; 

    
    public List<SedeResponseDTO> listar() {
        return sedeRepository.findByActivoTrue() 
                .stream()
                .map(this::convertirADTO) 
                .toList();
    }

    
    public SedeResponseDTO buscarPorId(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id)); 
        return convertirADTO(sede);
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

        Sede guardada = sedeRepository.save(sede); 
        return convertirADTO(guardada);
    }

    
    public SedeResponseDTO actualizar(Long id, SedeRequestDTO dto) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id)); 

        
        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setHorario(dto.getHorario());
        sede.setCapacidadMaxima(dto.getCapacidadMaxima());

        sedeRepository.save(sede); 
        return convertirADTO(sede);
    }

    
    public SedeResponseDTO registrarEntrada(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        
        if (sede.getOcupacionActual() >= sede.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La sede esta en su capacidad maxima, no se permiten mas entradas");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() + 1); 
        sedeRepository.save(sede); 
        return convertirADTO(sede);
    }

    
    public SedeResponseDTO registrarSalida(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        
        if (sede.getOcupacionActual() <= 0) {
            throw new IllegalArgumentException("La ocupacion de la sede ya esta en cero, no se puede decrementar");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() - 1); 
        sedeRepository.save(sede); 
        return convertirADTO(sede);
    }

    
    public SedeResponseDTO desactivar(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        if (!sede.getActivo()) { 
            throw new IllegalArgumentException("La sede ya esta desactivada");
        }

        sede.setActivo(false); 
        sedeRepository.save(sede); 
        return convertirADTO(sede);
    }

    
    
    private SedeResponseDTO convertirADTO(Sede sede) {
        
        int porcentaje = (sede.getOcupacionActual() * 100) / sede.getCapacidadMaxima();

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
