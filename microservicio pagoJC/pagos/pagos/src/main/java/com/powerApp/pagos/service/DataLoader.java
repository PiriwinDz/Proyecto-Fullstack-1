package cl.powerapp.pagos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.powerapp.pagos.model.EstadoPago;
import cl.powerapp.pagos.model.Pago;
import cl.powerapp.pagos.repository.PagoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(PagoRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                repository.save(
                    new Pago(
                        null,
                        1L, // usuarioId
                        101L, // membresiaId
                        new BigDecimal("19990"),
                        EstadoPago.PENDIENTE,
                        "Tarjeta de crédito",
                        "REF12345",
                        LocalDateTime.now(),
                        null
                    )
                );

                repository.save(
                    new Pago(
                        null,
                        2L,
                        102L,
                        new BigDecimal("29990"),
                        EstadoPago.PROCESADO,
                        "Transferencia bancaria",
                        "REF67890",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                );

                repository.save(
                    new Pago(
                        null,
                        3L,
                        103L,
                        new BigDecimal("9990"),
                        EstadoPago.FALLIDO,
                        "Tarjeta de débito",
                        "REF11111",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                );

                repository.save(
                    new Pago(
                        null,
                        4L,
                        104L,
                        new BigDecimal("49990"),
                        EstadoPago.PROCESADO,
                        "PayPal",
                        "REF22222",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                );
            }
        };
    }
}
