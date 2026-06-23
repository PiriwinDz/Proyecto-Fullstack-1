package com.example.alertas.service;


import com.example.alertas.dto.AlertaRequestDTO;


import com.example.alertas.dto.AlertaResponseDTO;


import com.example.alertas.exception.AlertaNoEncontradaException;


import com.example.alertas.model.Alerta;


import com.example.alertas.repository.AlertaRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


import java.util.List;


import java.util.stream.Collectors;


@Service


@RequiredArgsConstructor
public class AlertaService {

    
    private final AlertaRepository alertaRepository;

    
    public List<AlertaResponseDTO> listar() {

        
        return alertaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    
    public AlertaResponseDTO buscarPorId(Long id) {

        
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        
        return convertirDTO(alerta);
    }

    
    public AlertaResponseDTO crear(AlertaRequestDTO dto) {

        
        Alerta alerta = Alerta.builder()
                .titulo(dto.getTitulo())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .activa(true)
                .build();

        
        Alerta guardada = alertaRepository.save(alerta);

        
        return convertirDTO(guardada);
    }

    
    public AlertaResponseDTO desactivar(Long id) {

        
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        
        alerta.setActiva(false);

        
        alertaRepository.save(alerta);

        
        return convertirDTO(alerta);
    }

    
    private AlertaResponseDTO convertirDTO(Alerta alerta) {

        
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
