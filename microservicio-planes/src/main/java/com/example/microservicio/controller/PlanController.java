package com.example.microservicio.controller;

import com.example.microservicio.dto.PlanRequestDTO;
import com.example.microservicio.dto.PlanResponseDTO;
import com.example.microservicio.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponseDTO> createPlan(@Valid @RequestBody PlanRequestDTO requestDTO) {
        PlanResponseDTO response = planService.createPlan(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> getAllPlanes() {
        List<PlanResponseDTO> responses = planService.getAllPlanes();
        return ResponseEntity.ok(responses); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getPlanById(@PathVariable Long id) {
        PlanResponseDTO response = planService.getPlanById(id);
        return ResponseEntity.ok(response); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> updatePlan(@PathVariable Long id, @Valid @RequestBody PlanRequestDTO requestDTO) {
        PlanResponseDTO response = planService.updatePlan(id, requestDTO);
        return ResponseEntity.ok(response); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build(); 
    }
}
