package cl.powerapp.autenticacion.service;

import cl.powerapp.autenticacion.dto.*;
import cl.powerapp.autenticacion.exception.UsuarioNoEncontradoException;
import cl.powerapp.autenticacion.model.Usuario;
import cl.powerapp.autenticacion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponseDTO registrar(RegisterRequestDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una cuenta con el correo: " + dto.getCorreo());
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(dto.getRol())
                .activo(true)
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
                .orElseThrow(() -> new UsuarioNoEncontradoException("Correo o contrasena incorrectos"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Correo o contrasena incorrectos");
        }

        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("La cuenta esta desactivada");
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

    public UsuarioResponseDTO desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return convertirADTO(usuario);
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
