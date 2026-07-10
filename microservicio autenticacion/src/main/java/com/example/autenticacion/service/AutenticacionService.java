package com.example.autenticacion.service;

import com.example.autenticacion.dto.ActualizarUsuarioDTO;
import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.exception.CorreoYaRegistradoException;
import com.example.autenticacion.exception.CredencialesInvalidasException;
import com.example.autenticacion.exception.UsuarioNoEncontradoException;
import com.example.autenticacion.model.Usuario;
import com.example.autenticacion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO registrar(RegisterRequestDTO dto) {

        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new CorreoYaRegistradoException(dto.getCorreo());
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(dto.getRol())
                .activo(true)
                .creadoEn(LocalDateTime.now())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);

        String token = jwtService.generarToken(
                guardado.getId(),
                guardado.getCorreo(),
                guardado.getRol().name()
        );

        return AuthResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .correo(guardado.getCorreo())
                .rol(guardado.getRol().name())
                .token(token)
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        String token = jwtService.generarToken(
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getRol().name()
        );

        return AuthResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name())
                .token(token)
                .build();
    }


    public UsuarioResponseDTO buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        return convertirADTO(usuario);
    }


    public List<UsuarioResponseDTO> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public UsuarioResponseDTO actualizar(Long id, ActualizarUsuarioDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        if (!usuario.getCorreo().equals(dto.getCorreo())
                && usuarioRepository.existsByCorreo(dto.getCorreo())) {

            throw new CorreoYaRegistradoException(dto.getCorreo());
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);

        return convertirADTO(actualizado);
    }


    public void eliminar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        usuarioRepository.delete(usuario);
    }


    private UsuarioResponseDTO convertirADTO(Usuario usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name())
                .activo(usuario.getActivo())
                .creadoEn(usuario.getCreadoEn())
                .build();
    }

}