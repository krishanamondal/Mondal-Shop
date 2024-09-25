package com.mondal.mondal_shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Mondal Shop API",
                version = "v1",
                description = "Mondal Shop API Documentation",
                license = @License(name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(name = "Mondal Shop",
                        email = " mail.krishanamondal@gmail.com"),
                termsOfService = "http://www.apache.org/licenses/LICENSE-2.0.html"),
        servers = {
                @Server(
                        url = "http://localhost:7076",
                        description = "Local server"
                ),
                @Server(
                        url = "http://localhost:8081",
                        description = "Production  server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Jwt Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
