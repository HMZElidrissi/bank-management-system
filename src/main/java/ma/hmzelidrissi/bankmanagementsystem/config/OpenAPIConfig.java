package ma.hmzelidrissi.bankmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * To access the OpenAPI documentation, go to http://localhost:8082/swagger-ui.html
 * The @OpenAPIDefinition annotation is used to define the metadata of the OpenAPI specification.
 * The @SecurityScheme annotation is used to define a security scheme that can be used by the operations.
 */
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
                                url = "http://localhost:8082",
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
