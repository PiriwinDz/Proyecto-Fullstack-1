package com.example.alertas.service;

// dto request
import com.example.alertas.dto.AlertaRequestDTO;

// dto response
import com.example.alertas.dto.AlertaResponseDTO;

// exception personalizada
import com.example.alertas.exception.AlertaNoEncontradaException;

// modelo
import com.example.alertas.model.Alerta;

// repository
import com.example.alertas.repository.AlertaRepository;

// lombok constructor
import lombok.RequiredArgsConstructor;

// service spring
import org.springframework.stereotype.Service;

// listas
import java.util.List;

// streams
import java.util.stream.Collectors;

// indica logica negocio
@Service

// genera constructor automatico
@RequiredArgsConstructor
public class AlertaService {

    // repository inyectado
    private final AlertaRepository alertaRepository;

    // lista todas las alertas
    public List<AlertaResponseDTO> listar() {

        // busca todas las alertas y las convierte a dto
        return alertaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // busca alerta por id
    public AlertaResponseDTO buscarPorId(Long id) {

        // busca alerta o lanza exception
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        // retorna dto
        return convertirDTO(alerta);
    }

    // crea alerta
    public AlertaResponseDTO crear(AlertaRequestDTO dto) {

        // construye entidad
        Alerta alerta = Alerta.builder()
                .titulo(dto.getTitulo())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .activa(true)
                .build();

        // guarda en bd
        Alerta guardada = alertaRepository.save(alerta);

        // retorna dto
        return convertirDTO(guardada);
    }

    // desactiva alerta
    public AlertaResponseDTO desactivar(Long id) {

        // busca alerta
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        // cambia estado
        alerta.setActiva(false);

        // guarda cambios
        alertaRepository.save(alerta);

        // retorna dto
        return convertirDTO(alerta);
    }

    // convierte entidad a dto
    private AlertaResponseDTO convertirDTO(Alerta alerta) {

        // construye dto
        return AlertaResponseDTO.builder()
                .id(alerta.getId())
                .titulo(alerta.getTitulo())
                .mensaje(alerta.getMensaje())
                .tipo(alerta.getTipo())
                .activa(alerta.getActiva())
                .creadaEn(alerta.getCreadaEn())
                .build();
    }
}
