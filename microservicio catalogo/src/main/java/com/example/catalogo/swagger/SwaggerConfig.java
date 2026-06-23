package com.example.catalogo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI catalogoOpenAPI(){

        return new OpenAPI()
            .info(new Info()


                    .title("API de catalogo")

                    .description("API encargada de la gestión del catalogo de ejercicios")

                    .version("1.0")

                    .contact(new Contact()
                            .name("Power")
                            .email("catalogo@power.cl")            
                )   
        
        );
    }

}
