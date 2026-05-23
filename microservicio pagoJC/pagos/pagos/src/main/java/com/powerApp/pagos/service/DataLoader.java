package com.powerApp.pagos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.powerApp.pagos.model.EstadoPago;
import com.powerApp.pagos.model.Pago;
import com.powerApp.pagos.repository.PagoRepository;

@Component
@SuppressWarnings("all")
public class DataLoader implements CommandLineRunner {

        private final PagoRepository repository;

        public DataLoader(PagoRepository repository) {
                this.repository = repository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (args != null && this.repository.count() == 0) {

                        this.repository.save(crearPago(
                                        1L, 101L, "19990",
                                        EstadoPago.PENDIENTE,
                                        "Tarjeta de crédito",
                                        "REF12345"));

                        this.repository.save(crearPago(
                                        2L, 102L, "29990",
                                        EstadoPago.APROBADO,
                                        "Transferencia bancaria",
                                        "REF67890"));

                        this.repository.save(crearPago(
                                        3L, 103L, "9990",
                                        EstadoPago.RECHAZADO,
                                        "Tarjeta de débito",
                                        "REF11111"));

                        this.repository.save(crearPago(
                                        4L, 104L, "49990",
                                        EstadoPago.APROBADO,
                                        "PayPal",
                                        "REF22222"));
                }
        }

        private static Pago crearPago(
                        Long usuarioId,
                        Long membresiaId,
                        String monto,
                        EstadoPago estado,
                        String metodoPago,
                        String referencia) {

                Pago pago = new Pago();
                pago.setUsuarioId(usuarioId);
                pago.setMembresiaId(membresiaId);
                pago.setMonto(new BigDecimal(monto));
                pago.setEstado(estado);
                pago.setMetodoPago(metodoPago);
                pago.setReferenciaPasarela(referencia);
                pago.setCreadoEn(LocalDateTime.now());

                if (estado == EstadoPago.APROBADO
                                || estado == EstadoPago.RECHAZADO
                                || estado == EstadoPago.REEMBOLSADO) {
                        pago.setProcesadoEn(LocalDateTime.now());
                }

                return pago;
        }
}
