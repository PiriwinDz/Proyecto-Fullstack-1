
package com.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.autenticacion.AutenticacionApplication;
import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.service.AutenticacionService;
import com.example.autenticacion.controller.AutenticacionController;

@WebMvcTest(controllers =AutenticacionController.class)
@ContextConfiguration(classes = AutenticacionApplication.class)
public class AutenticacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AutenticacionService autenticacionService;

    @Test
    void registrarUsuario() throws Exception {

        String usuarioJson = """
        {
            "nombre":"Matias",
            "correo":"matias@test.cl",
            "password":"12345678",
            "rol":"ATLETA"
        }
        """;

        AuthResponseDTO respuesta = AuthResponseDTO.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .rol("ATLETA")
                .token("token-test")
                .build();

        when(autenticacionService.registrar(any()))
                .thenReturn(respuesta);

        mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(usuarioJson))
                .andExpect(status().isCreated());
    }

    @Test
    void loginUsuario() throws Exception {

        String loginJson = """
        {
            "correo":"matias@test.cl",
            "password":"12345678"
        }
        """;

        AuthResponseDTO respuesta = AuthResponseDTO.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .rol("ATLETA")
                .token("token-test")
                .build();

        when(autenticacionService.login(any()))
                .thenReturn(respuesta);

        mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(loginJson))
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

        UsuarioResponseDTO usuario = UsuarioResponseDTO.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .rol("ATLETA")
                .activo(true)
                .build();

        when(autenticacionService.buscarPorId(1L))
                .thenReturn(usuario);

        mockMvc.perform(get("/auth/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void desactivarUsuario() throws Exception {

        UsuarioResponseDTO usuario = UsuarioResponseDTO.builder()
                .id(1L)
                .nombre("Matias")
                .correo("matias@test.cl")
                .rol("ATLETA")
                .activo(false)
                .build();

        when(autenticacionService.desactivar(1L))
                .thenReturn(usuario);

        mockMvc.perform(put("/auth/usuarios/1/desactivar"))
                .andExpect(status().isOk());
    }
}

