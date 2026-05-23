package com.example.sede.service;

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.exception.SedeNoEncontradaException;
import com.example.sede.model.Sede;
import com.example.sede.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service // lo registra como componente de Spring para poder inyectarlo
@RequiredArgsConstructor // genera el constructor con los campos final (inyeccion de dependencias)
public class SedeService {

    private final SedeRepository sedeRepository; // acceso a la BD

    // retorna todas las sedes que esten activas
    public List<SedeResponseDTO> listar() {
        return sedeRepository.findByActivoTrue() // solo las sedes con activo = true
                .stream()
                .map(this::convertirADTO) // convierte cada Sede a SedeResponseDTO
                .toList();
    }

    // busca una sede por id, lanza excepcion si no existe
    public SedeResponseDTO buscarPorId(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id)); // lanza 404 si no existe
        return convertirADTO(sede);
    }

    // crea una nueva sede en la BD
    public SedeResponseDTO crear(SedeRequestDTO dto) {
        Sede sede = Sede.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .horario(dto.getHorario())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .ocupacionActual(0) // siempre empieza en 0 al crear
                .activo(true)
                .creadoEn(LocalDateTime.now()) // fecha actual de creacion
                .build();

        Sede guardada = sedeRepository.save(sede); // guarda en la BD
        return convertirADTO(guardada);
    }

    // actualiza los datos de una sede existente
    public SedeResponseDTO actualizar(Long id, SedeRequestDTO dto) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id)); // lanza 404 si no existe

        // actualiza solo los campos que llegaron en el DTO
        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setHorario(dto.getHorario());
        sede.setCapacidadMaxima(dto.getCapacidadMaxima());

        sedeRepository.save(sede); // guarda los cambios en la BD
        return convertirADTO(sede);
    }

    // registra la entrada de una persona a la sede
    public SedeResponseDTO registrarEntrada(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        // verifica que la sede no este llena antes de permitir la entrada
        if (sede.getOcupacionActual() >= sede.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La sede esta en su capacidad maxima, no se permiten mas entradas");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() + 1); // incrementa la ocupacion en 1
        sedeRepository.save(sede); // guarda el cambio en la BD
        return convertirADTO(sede);
    }

    // registra la salida de una persona de la sede
    public SedeResponseDTO registrarSalida(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        // verifica que la ocupacion no baje de 0
        if (sede.getOcupacionActual() <= 0) {
            throw new IllegalArgumentException("La ocupacion de la sede ya esta en cero, no se puede decrementar");
        }

        sede.setOcupacionActual(sede.getOcupacionActual() - 1); // decrementa la ocupacion en 1
        sedeRepository.save(sede); // guarda el cambio en la BD
        return convertirADTO(sede);
    }

    // desactiva una sede sin borrarla de la BD (soft delete)
    public SedeResponseDTO desactivar(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new SedeNoEncontradaException(id));

        if (!sede.getActivo()) { // verifica que no este ya desactivada
            throw new IllegalArgumentException("La sede ya esta desactivada");
        }

        sede.setActivo(false); // cambia el estado a inactivo
        sedeRepository.save(sede); // guarda el cambio en la BD
        return convertirADTO(sede);
    }

    // metodo privado reutilizable que convierte Sede a SedeResponseDTO
    // se usa para no repetir el mismo codigo de conversion en cada metodo
    private SedeResponseDTO convertirADTO(Sede sede) {
        // calcula el porcentaje de ocupacion actual
        int porcentaje = (sede.getOcupacionActual() * 100) / sede.getCapacidadMaxima();

        return SedeResponseDTO.builder()
                .id(sede.getId())
                .nombre(sede.getNombre())
                .direccion(sede.getDireccion())
                .horario(sede.getHorario())
                .capacidadMaxima(sede.getCapacidadMaxima())
                .ocupacionActual(sede.getOcupacionActual())
                .porcentajeOcupacion(porcentaje) // campo calculado, no existe en la BD
                .activo(sede.getActivo())
                .creadoEn(sede.getCreadoEn())
                .build();
    }
}
