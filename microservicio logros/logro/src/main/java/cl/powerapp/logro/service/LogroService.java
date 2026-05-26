package cl.powerapp.logro.service;


import cl.powerapp.logro.dto.DesbloquearLogroDTO;
import cl.powerapp.logro.dto.LogroListadoDTO;
import cl.powerapp.logro.dto.LogroSimpleDTO;
import cl.powerapp.logro.dto.UsuarioLogrosDTO;
import cl.powerapp.logro.exception.LogroNoEncontradoException;
import cl.powerapp.logro.exception.LogroYaDesbloqueadoException;
import cl.powerapp.logro.exception.UsuarioNoEncontradoException;
import cl.powerapp.logro.model.Logro;
import cl.powerapp.logro.model.LogroUsuario;
import cl.powerapp.logro.model.Usuario;
import cl.powerapp.logro.repository.LogroRepository;
import cl.powerapp.logro.repository.LogroUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LogroService {

    @Autowired
    private LogroRepository repository;

    @Autowired
    private LogroUsuarioRepository logroUsuarioRepository;

    public Logro guardar(Logro logro) {
        return repository.save(logro);
    }

    public LogroUsuario desbloquearLogro(
            DesbloquearLogroDTO dto){

        Optional<Logro> optionalLogro =
                repository.findById(dto.getLogroId());

        if(optionalLogro.isEmpty()){

            throw new LogroNoEncontradoException(
                    "El logro no existe");
        }   

        Usuario usuario =
                obtenerUsuario(dto.getUsuarioId());

        if(usuario == null){

            throw new UsuarioNoEncontradoException(
                    "El usuario no existe");
        }

        boolean yaExiste =
                logroUsuarioRepository
                .existsByUsuarioIdAndLogroId(
                        dto.getUsuarioId(),
                        dto.getLogroId());

        if(yaExiste){

            throw new LogroYaDesbloqueadoException(
                    "El usuario ya desbloqueó este logro");
        }

        LogroUsuario nuevo = new LogroUsuario();

        nuevo.setUsuarioId(dto.getUsuarioId());
        nuevo.setLogroId(dto.getLogroId());
        nuevo.setFechaDesbloqueo(LocalDate.now());

        return logroUsuarioRepository.save(nuevo);
    }

    public List<LogroListadoDTO> listarDTO() {

        List<Logro> logros = repository.findAll(); 

        List<LogroListadoDTO> resultado = new ArrayList<>();

        for(Logro logro : logros){ 

            LogroListadoDTO dto = convertirListadoDTO(logro);

            resultado.add(dto); 

        } 

        return resultado; 
    }

    public LogroSimpleDTO obtenerDetalleSimple(Long id) {


            Logro logro = repository.findById(id)
                .orElseThrow(() ->
                    new LogroNoEncontradoException(
                    "El logro no existe"));

        return convertirSimpleDTO(logro);
    }

    public List<LogroListadoDTO> obtenerLogrosUsuario(Long usuarioId) {

        List<LogroUsuario> relaciones =
                        logroUsuarioRepository.findByUsuarioId(usuarioId);

        List<LogroListadoDTO> resultado = new ArrayList<>();

        for(LogroUsuario relacion: relaciones){

            Optional<Logro> optionalLogro = 
                            repository.findById(relacion.getLogroId());
            
            if(optionalLogro.isEmpty()){

                throw new RuntimeException("Logro no encontrado");
            }

            Logro logro = optionalLogro.get();

            LogroListadoDTO dto = 
                        new LogroListadoDTO(
                            logro.getId(),
                            logro.getNombre(),
                            logro.getDescripcion(),
                            logro.getPuntos()
                        );
            resultado.add(dto);
        }

        return resultado;
    }

    public Logro actualizar(Long id, Logro logro) {


        Logro logroExistente = repository.findById(id)
                    .orElseThrow(() ->
                        new LogroNoEncontradoException(
                            "El logro no existe"));

        logroExistente.setNombre(logro.getNombre());
        logroExistente.setDescripcion(logro.getDescripcion());
        logroExistente.setPuntos(logro.getPuntos());

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

        String url = "http://localhost:8089/auth/usuarios/"+ usuarioId;

        try{

            return restTemplate.getForObject(url,Usuario.class);

        }catch(HttpClientErrorException.NotFound ex){

            throw new UsuarioNoEncontradoException(
                    "El usuario no existe");
        }
    }

    public UsuarioLogrosDTO obtenerLogrosConUsuario(Long usuarioId){

        Usuario usuario = obtenerUsuario(usuarioId);
        List<LogroListadoDTO> logros = obtenerLogrosUsuario(usuarioId); 
        UsuarioLogrosDTO respuesta = new UsuarioLogrosDTO(); 
        respuesta.setNombreUsuario( usuario.getNombre()); 
        respuesta.setLogros(logros); 
        
        return respuesta; 

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
                logro.getNombre(),
                logro.getDescripcion(),
                logro.getPuntos()
                
        );
    }
   
}
