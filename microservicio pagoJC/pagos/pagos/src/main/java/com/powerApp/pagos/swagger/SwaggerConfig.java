package com.powerApp.pagos.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagos") // Nombre de la API que aparecerá en Swagger
                        .description("API REST para la gestión de pagos") // Descripción general del proyecto
                        .version("1.0") // Versión de la API
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")));
    }
}
