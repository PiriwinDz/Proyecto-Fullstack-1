package com.example.alertas;

// importa spring boot
import org.springframework.boot.SpringApplication;

// importa spring boot application
import org.springframework.boot.autoconfigure.SpringBootApplication;

// indica que esta es la clase principal del microservicio
@SpringBootApplication
public class AlertasApplication {

    // metodo principal que inicia spring boot
    public static void main(String[] args) {

        // levanta el microservicio
        SpringApplication.run(AlertasApplication.class, args);
    }
}
