package com.powerapp.resenas.service;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.powerapp.resenas.model.Resena;
import com.powerapp.resenas.repository.ResenaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(ResenaRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                repository.save(
                    new Resena(
                        null,
                        1L,
                        201L,
                        5,
                        "Excelente rutina de pecho",
                        LocalDateTime.now()
                    )
                );

                repository.save(
                    new Resena(
                        null,
                        2L,
                        202L,
                        4,
                        "Muy buena rutina para principiantes",
                        LocalDateTime.now()
                    )
                );

                repository.save(
                    new Resena(
                        null,
                        3L,
                        203L,
                        3,
                        "La rutina estuvo normal",
                        LocalDateTime.now()
                    )
                );

                repository.save(
                    new Resena(
                        null,
                        4L,
                        204L,
                        5,
                        "Excelente entrenamiento de piernas",
                        LocalDateTime.now()
                    )
                );
            }
        };
    }
}