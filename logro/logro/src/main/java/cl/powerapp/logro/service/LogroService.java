package cl.powerapp.logro.service;


import cl.powerapp.logro.dto.LogroListadoDTO;
import cl.powerapp.logro.dto.LogroSimpleDTO;
import cl.powerapp.logro.model.Logro;
import cl.powerapp.logro.model.Usuario;
import cl.powerapp.logro.repository.LogroRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogroService {

    private final LogroRepository repository;

    public LogroService(LogroRepository repository) {
        this.repository = repository;
    }

    public Logro guardar(Logro logro) {
        return repository.save(logro);
    }

    public List<LogroListadoDTO> listarDTO() {

        return repository.findAll()
                .stream()
                .map(this::convertirListadoDTO)
                .collect(Collectors.toList());
    }

    public LogroSimpleDTO obtenerDetalleSimple(Long id) {

        Logro logro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Logro no encontrado"));

        return convertirSimpleDTO(logro);
    }

    public List<LogroListadoDTO> obtenerPorUsuarioDTO(Long usuarioId) {

        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirListadoDTO)
                .collect(Collectors.toList());
    }

    public Logro actualizar(Long id, Logro logro) {

        Logro logroExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Logro no encontrado"));

        logroExistente.setUsuarioId(logro.getUsuarioId());
        logroExistente.setNombre(logro.getNombre());
        logroExistente.setDescripcion(logro.getDescripcion());
        logroExistente.setPuntos(logro.getPuntos());
        logroExistente.setFechaDesbloqueo(logro.getFechaDesbloqueo());

        return repository.save(logroExistente);
    }

    public void eliminar(Long id) {

        Logro logro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Logro no encontrado"));

        repository.delete(logro);
    }

    public Usuario obtenerUsuario(Long usuarioId) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/usuarios/" + usuarioId;

        return restTemplate.getForObject(url, Usuario.class);
    }

    public LogroSimpleDTO convertirSimpleDTO(Logro logro) {

        return new LogroSimpleDTO(
                logro.getId(),
                logro.getNombre()
        );
    }

    public LogroListadoDTO convertirListadoDTO(Logro logro) {

        return new LogroListadoDTO(
                logro.getId(),
                logro.getUsuarioId(),
                logro.getNombre(),
                logro.getDescripcion(),
                logro.getPuntos(),
                logro.getFechaDesbloqueo()
        );
    }
}
