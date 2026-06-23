package com.example.alertas.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI logrosOpenAPI(){

        return new OpenAPI()
            .info(new Info()


                    .title("API de alertas")

                    .description("API encargada de la gestión de notificaciones y alertas")

                    .version("1.0")

                    .contact(new Contact()
                            .name("Power")
                            .email("soporte@power.cl")            
                )   
        
        );
    }

}