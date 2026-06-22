package com.powerapp.resenas.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reseñasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reseñas") // Nombre de la API que aparecerá en Swagger
                        .description("API REST para la gestión de reseñas") // Descripción general del proyecto
                        .version("1.0") // Versión de la API
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")));
    }
}
