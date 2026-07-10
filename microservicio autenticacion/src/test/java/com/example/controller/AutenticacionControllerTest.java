package com.example.controller;

import com.example.autenticacion.AutenticacionApplication;
import com.example.autenticacion.controller.AutenticacionController;
import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.service.AutenticacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AutenticacionController.class)
@ContextConfiguration(classes = AutenticacionApplication.class)
public class AutenticacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AutenticacionService autenticacionService;

    @Test
    void registrarUsuario() throws Exception {

        String json = """
                {
                        "nombre":"Matias",
                        "correo":"matias@test.cl",
                        "password":"Matias123",
                        "rol":"ATLETA"
                }
                """;

        when(autenticacionService.registrar(any()))
                .thenReturn(AuthResponseDTO.builder().build());

        mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void loginUsuario() throws Exception {

        String json = """
        {
            "correo":"matias@test.cl",
            "password":"12345678"
        }
        """;

        when(autenticacionService.login(any()))
                .thenReturn(AuthResponseDTO.builder().build());

        mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void listarUsuarios() throws Exception {

        when(autenticacionService.listarTodos())
                .thenReturn(List.of());

        mockMvc.perform(get("/auth/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarUsuarioPorId() throws Exception {

        when(autenticacionService.buscarPorId(1L))
                .thenReturn(UsuarioResponseDTO.builder().build());

        mockMvc.perform(get("/auth/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarUsuario() throws Exception {

        String json = """
        {
            "nombre":"Matias",
            "correo":"matias@test.cl",
            "rol":"ATLETA"
        }
        """;

        when(autenticacionService.actualizar(any(), any()))
                .thenReturn(UsuarioResponseDTO.builder().build());

        mockMvc.perform(put("/auth/usuarios/1")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarUsuario() throws Exception {

        doNothing().when(autenticacionService).eliminar(1L);

        mockMvc.perform(delete("/auth/usuarios/1"))
                .andExpect(status().isNoContent());
    }

}