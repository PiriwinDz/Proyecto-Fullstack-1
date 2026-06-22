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
                        .title("API de Pagos")
                        .description("API REST para la gestión de pagos")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")));
    }
}
