package com.powerApp.pagos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.repository.PagoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(PagoRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                repository.save(new Pago(null, 1L, 101L, new BigDecimal("19990"),
                        EstadoPago.APROBADO, "Tarjeta Crédito", "TXN-001",
                        LocalDateTime.now(), LocalDateTime.now()));

                repository.save(new Pago(null, 2L, 102L, new BigDecimal("15990"),
                        EstadoPago.PENDIENTE, "Débito", "TXN-002",
                        LocalDateTime.now(), null));

                repository.save(new Pago(null, 3L, 103L, new BigDecimal("24990"),
                        EstadoPago.RECHAZADO, "Transferencia", "TXN-003",
                        LocalDateTime.now(), LocalDateTime.now()));

                repository.save(new Pago(null, 4L, 104L, new BigDecimal("12990"),
                        EstadoPago.REEMBOLSADO, "PayPal", "TXN-004",
                        LocalDateTime.now(), LocalDateTime.now()));
            }
        };
    }
}
