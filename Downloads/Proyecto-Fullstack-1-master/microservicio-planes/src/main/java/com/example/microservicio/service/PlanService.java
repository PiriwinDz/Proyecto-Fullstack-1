package com.example.microservicio.service;

import com.example.microservicio.dto.PlanRequestDTO;
import com.example.microservicio.dto.PlanResponseDTO;
import com.example.microservicio.exception.ResourceNotFoundException;
import com.example.microservicio.model.Plan;
import com.example.microservicio.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanResponseDTO createPlan(PlanRequestDTO requestDTO) {
        Plan plan = Plan.builder()
                .nombre(requestDTO.getNombre())
                .coste(requestDTO.getCoste())
                .descripcion(requestDTO.getDescripcion())
                .build();
                
        Plan savedPlan = planRepository.save(plan);
        return mapToDTO(savedPlan);
    }

    public List<PlanResponseDTO> getAllPlanes() {
        return planRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PlanResponseDTO getPlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el plan con ID: " + id));
        return mapToDTO(plan);
    }

    public PlanResponseDTO updatePlan(Long id, PlanRequestDTO requestDTO) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el plan con ID: " + id));
        
        plan.setNombre(requestDTO.getNombre());
        plan.setCoste(requestDTO.getCoste());
        plan.setDescripcion(requestDTO.getDescripcion());
        
        Plan updatedPlan = planRepository.save(plan);
        return mapToDTO(updatedPlan);
    }

    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el plan con ID: " + id);
        }
        planRepository.deleteById(id);
    }

    private PlanResponseDTO mapToDTO(Plan plan) {
        return PlanResponseDTO.builder()
                .id(plan.getId())
                .nombre(plan.getNombre())
                .coste(plan.getCoste())
                .descripcion(plan.getDescripcion())
                .build();
    }
}