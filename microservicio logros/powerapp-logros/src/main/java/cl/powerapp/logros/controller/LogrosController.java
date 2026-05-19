package cl.powerapp.logros.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.powerapp.logros.model.Logro;
import cl.powerapp.logros.service.LogrosService;

@RestController
@RequestMapping("/logros")
public class LogrosController {

    private final LogrosService service;

    public LogrosController(LogrosService service){
        this.service = service;
    }

    @GetMapping
    public List<Logro> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public Logro obtener(@PathVariable Integer id){
        return service.obtenerPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Logro> obtenerPorUsuario(@PathVariable Long usuarioId){
        return service.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public Logro guardar(@RequestBody Logro logro){
        return service.guardar(logro);
    }

    @PutMapping("/{id}")
    public Logro actualizar(@PathVariable Integer id,@RequestBody Logro logro){

        logro.setId(id);

        return service.guardar(logro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
