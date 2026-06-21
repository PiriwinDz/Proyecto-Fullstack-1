package cl.powerapp.logro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.powerapp.logro.dto.DesbloquearLogroDTO;
import cl.powerapp.logro.dto.LogroListadoDTO;
import cl.powerapp.logro.dto.LogroSimpleDTO;
import cl.powerapp.logro.dto.UsuarioLogrosDTO;
import cl.powerapp.logro.model.Logro;
import cl.powerapp.logro.model.LogroUsuario;
import cl.powerapp.logro.service.LogroService;

@WebMvcTest(LogroController.class)
public class LogroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LogroService service;

    @Test
    void listarLogros() throws Exception{
    List<LogroListadoDTO> logros = List.of(
        new LogroListadoDTO(
            1L,
            "Primer logro",
            "Descripción del logro",
            100)
        );
        
        when(service.listarDTO()).thenReturn(logros);

        mockMvc.perform(get("/logros/listar"))
               .andExpect(status().isOk());
    }

    @Test
    void crearLogro() throws Exception{

        String logroJson = """
                {
                    "nombre": "Primer Logro",
                    "descripcion": "Descripcion del logro",
                    "puntos": 100
                }
                """;
        Logro logroGuardado = new Logro(
                1L,
            "Primer Logro",
       "Descripcion del logro",
            100

        );
        
        when(service.guardar(any(Logro.class))).thenReturn(logroGuardado);
        mockMvc.perform(post("/logros/agregar")
               .contentType(APPLICATION_JSON)
               .content(logroJson))
               .andExpect(status().isCreated());
    }

    @Test
    void obtenerDetalleSimple() throws Exception {

        LogroSimpleDTO dto = new LogroSimpleDTO(
            1L,
            "Primer Logro"
        );

        when(service.obtenerDetalleSimple(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/logros/1/detalle-simple"))
               .andExpect(status().isOk());
    }

    @Test
    void obtenerLogrosUsuario() throws Exception {

        UsuarioLogrosDTO dto = new UsuarioLogrosDTO();

        dto.setNombreUsuario("Juan");

        dto.setLogros(List.of(
                new LogroListadoDTO(
                    1L,
                    "Primer Logro",
                    "Descripcion",
                    100
            )
        ));

        when(service.obtenerLogrosConUsuario(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/logros/usuario/1"))
               .andExpect(status().isOk());
    }

    @Test
    void actualizarLogro() throws Exception {

        String logroJson = """
            {
                "nombre":"Logro Actualizado",
                "descripcion":"Nueva descripcion",
                "puntos":200
            }
            """;

        Logro actualizado = new Logro(
            1L,
            "Logro Actualizado",
            "Nueva descripcion",
            200
        );

        when(service.actualizar(anyLong(),any(Logro.class))).thenReturn(actualizado);
        mockMvc.perform(put("/logros/actualizar/1")
            .contentType(APPLICATION_JSON)
            .content(logroJson))
            .andExpect(status().isOk());
    }

    @Test
    void desbloquearLogro() throws Exception {

        String dtoJson = """
            {
                "usuarioId":1,
                "logroId":1
            }
            """;

        LogroUsuario logroUsuario = new LogroUsuario();

        logroUsuario.setUsuarioId(1L);
        logroUsuario.setLogroId(1L);

        when(service.desbloquearLogro(any(DesbloquearLogroDTO.class))).thenReturn(logroUsuario);
        mockMvc.perform(post("/logros/desbloquear")
            .contentType(APPLICATION_JSON)
            .content(dtoJson))
            .andExpect(status().isCreated());
    }

    @Test
    void eliminarLogro() throws Exception {

    mockMvc.perform(delete("/logros/eliminar/1"))
           .andExpect(status().isNoContent());
    }
}
