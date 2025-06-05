package com.accenture.challenge_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API prueba Dev Backend",
                version = "1.0.0",
                description = "Microservicio para gestionar franquicias, sucursales y productos.",
                contact = @Contact(
                        name = "Alex Cudriz",
                        email = "alex.d.cudriz@accenture.com"
                )
        )
)
public class SwaggerConfig {
}
