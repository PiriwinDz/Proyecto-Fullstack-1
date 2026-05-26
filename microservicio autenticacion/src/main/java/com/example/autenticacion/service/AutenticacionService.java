package com.example.autenticacion.service;

import com.example.autenticacion.dto.AuthResponseDTO;
import com.example.autenticacion.dto.LoginRequestDTO;
import com.example.autenticacion.dto.RegisterRequestDTO;
import com.example.autenticacion.dto.UsuarioResponseDTO;
import com.example.autenticacion.exception.UsuarioNoEncontradoException;
import com.example.autenticacion.model.Usuario;
import com.example.autenticacion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service // lo registra como componente de Spring
@RequiredArgsConstructor // genera el constructor con los campos final (inyeccion de dependencias)
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository; // acceso a la BD
    private final JwtService jwtService;               // generacion de tokens
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // encriptador de passwords

    // registra un nuevo usuario en el sistema
    public AuthResponseDTO registrar(RegisterRequestDTO dto) {
        // verifica que el correo no este registrado antes de crear el usuario
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una cuenta con el correo: " + dto.getCorreo());
        }

        // construye el objeto Usuario con los datos del DTO
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword())) // encripta el password antes de guardar
                .rol(dto.getRol())
                .activo(true)
                .creadoEn(LocalDateTime.now()) // asigna la fecha actual
                .build();

        Usuario guardado = usuarioRepository.save(usuario); // guarda en la BD y retorna el objeto con id generado

        // genera el JWT con el id, correo y rol del usuario recien creado
        String token = jwtService.generarToken(
                guardado.getId(),
                guardado.getCorreo(),
                guardado.getRol().name()
        );

        // retorna los datos del usuario y el token al controller
        return AuthResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .correo(guardado.getCorreo())
                .rol(guardado.getRol().name())
                .token(token)
                .build();
    }

    // valida credenciales y genera token si son correctas
    public AuthResponseDTO login(LoginRequestDTO dto) {
        // busca el usuario por correo, si no existe lanza excepcion con mensaje generico (seguridad)
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Correo o contrasena incorrectos"));

        // compara el password ingresado con el hash guardado en la BD
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Correo o contrasena incorrectos"); // mismo mensaje para no dar pistas
        }

        // verifica que la cuenta no este desactivada
        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("La cuenta esta desactivada");
        }

        // genera el JWT con los datos del usuario autenticado
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

    // busca un usuario por id, lanza excepcion si no existe
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        return convertirADTO(usuario); // convierte a DTO antes de devolver (sin password)
    }

    // retorna todos los usuarios del sistema
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO) // convierte cada Usuario a UsuarioResponseDTO
                .toList();
    }

    // desactiva un usuario sin borrarlo de la BD
    public UsuarioResponseDTO desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuario.setActivo(false); // cambia el estado a inactivo
        usuarioRepository.save(usuario); // guarda el cambio en la BD
        return convertirADTO(usuario);
    }

    // metodo privado reutilizable que convierte Usuario a UsuarioResponseDTO
    // se usa para no repetir el mismo codigo en cada metodo
    private UsuarioResponseDTO convertirADTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name()) // convierte el enum a String
                .activo(usuario.getActivo())
                .creadoEn(usuario.getCreadoEn())
                .build();
    }
}
