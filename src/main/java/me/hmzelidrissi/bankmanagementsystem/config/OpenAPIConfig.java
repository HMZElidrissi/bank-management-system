package me.hmzelidrissi.bankmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank Management System",
                version = "v1",
                description = "A simple bank management system",
                contact = @Contact(
                        name = "Hamza El Idrissi",
                        url = "https://hmzelidrissi.ma"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                ),
                termsOfService = "some terms of service"
        ),

                servers = {
                        @Server(
                                url = "http://localhost:8080",
                                description = "Local server"
                        ),
                },
        security = {
                @SecurityRequirement(
                        name = "Authorization"
                )
        }
        )
@SecurityScheme(
        name = "Authorization",
        description = "JWT token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
