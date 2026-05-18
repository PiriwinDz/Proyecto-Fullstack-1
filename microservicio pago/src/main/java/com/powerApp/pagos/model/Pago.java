package com.powerApp.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(nullable = false)
    private Long usuarioId;

    @NotNull(message = "La membresía asociada es obligatoria")
    @Column(nullable = false)
    private Long membresiaId;

    @NotNull(message = "El monto no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Column(nullable = false)
    private BigDecimal monto;

    @NotNull(message = "El estado del pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @NotBlank(message = "El método de pago no puede estar en blanco")
    @Column(nullable = false)
    private String metodoPago;

    private String referenciaPasarela;

    @Column(updatable = false)
    private LocalDateTime creadoEn;

    private LocalDateTime procesadoEn;

    @PrePersist
    public void prePersist() {
        creadoEn = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoPago.PENDIENTE;
        }
    }
}