package com.example.alertas.service;

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.dto.AlertaResponseDTO;
import com.example.alertas.dto.UsuarioDTO;
import com.example.alertas.exception.AlertaNoEncontradaException;
import com.example.alertas.exception.UsuarioNoEncontradoException;
import com.example.alertas.model.Alerta;
import com.example.alertas.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final RestTemplate restTemplate;

    public List<AlertaResponseDTO> listar() {

        List<Alerta> alertas = alertaRepository.findAll();
        List<AlertaResponseDTO> respuesta = new ArrayList<>();

        for (Alerta alerta : alertas) {
            respuesta.add(convertirDTO(alerta));
        }

        return respuesta;
    }

    public AlertaResponseDTO buscarPorId(Long id) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        return convertirDTO(alerta);
    }

    public AlertaResponseDTO crear(AlertaRequestDTO dto) {

        UsuarioDTO usuario = obtenerUsuario(dto.getUsuarioId());

        Alerta alerta = new Alerta();

        alerta.setUsuarioId(usuario.getId());
        alerta.setTitulo(dto.getTitulo());
        alerta.setMensaje(dto.getMensaje());
        alerta.setTipo(dto.getTipo());
        alerta.setActiva(true);

        Alerta guardada = alertaRepository.save(alerta);

        return convertirDTO(guardada);
    }

    public AlertaResponseDTO actualizar(Long id, AlertaRequestDTO dto) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        UsuarioDTO usuario = obtenerUsuario(dto.getUsuarioId());

        alerta.setUsuarioId(usuario.getId());
        alerta.setTitulo(dto.getTitulo());
        alerta.setMensaje(dto.getMensaje());
        alerta.setTipo(dto.getTipo());

        Alerta actualizada = alertaRepository.save(alerta);

        return convertirDTO(actualizada);
    }

    public void eliminar(Long id) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaNoEncontradaException(id));

        alertaRepository.delete(alerta);
    }

    public UsuarioDTO obtenerUsuario(Long usuarioId) {

        String url = "http://MICROSERVICIO-USUARIOS/auth/usuarios/" + usuarioId;

        try {

            return restTemplate.getForObject(
                    url,
                    UsuarioDTO.class);

        } catch (HttpClientErrorException.NotFound ex) {

            throw new UsuarioNoEncontradoException(usuarioId);

        }
    }

    private AlertaResponseDTO convertirDTO(Alerta alerta) {

        AlertaResponseDTO dto = new AlertaResponseDTO();

        dto.setId(alerta.getId());
        dto.setUsuarioId(alerta.getUsuarioId());
        dto.setTitulo(alerta.getTitulo());
        dto.setMensaje(alerta.getMensaje());
        dto.setTipo(alerta.getTipo());
        dto.setActiva(alerta.getActiva());
        dto.setCreadaEn(alerta.getCreadaEn());

        return dto;
    }
}