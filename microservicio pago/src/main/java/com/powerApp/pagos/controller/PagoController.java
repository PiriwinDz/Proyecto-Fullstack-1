package com.powerApp.pagos.controller;

import com.powerApp.pagos.dto.PagoRequestDTO;
import com.powerApp.pagos.dto.PagoResponseDTO;
import com.powerApp.pagos.service.PagoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Validated
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesar(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(dto));
    }

}
