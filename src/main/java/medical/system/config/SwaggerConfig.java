package medical.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "CONSULTORIO API",
                description = "This app provides a REST API for medical appointments",
                termsOfService = "www.julianpena.com/terminos_y_condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Julian Peña",
                        url = "https://julianpeña.com",
                        email = "julianpr8@hotmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        }
)

public class SwaggerConfig { }
