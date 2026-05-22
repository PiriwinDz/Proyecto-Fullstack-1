package cl.powerapp.logros.service;

import cl.powerapp.logros.model.*;
import cl.powerapp.logros.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LogrosService {

    private final LogroRepository repository;

    public LogrosService(LogroRepository repository){
        this.repository = repository;
    }

    public List<Logro> listar() {
        return repository.findAll();
    }

    public Logro obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Logro> obtenerPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Logro guardar(Logro logro) {
        return repository.save(logro);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    public Usuario obtenerUsuario(Long usuarioId) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/usuarios/" + usuarioId;

        return restTemplate.getForObject(url, Usuario.class);
    }

    
}

