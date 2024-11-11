package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The @SecurityRequirement annotation is used to define a security requirement for an operation.
 * If the whole API has the same security requirements, you can define them at the class level using the @SecurityRequirement annotation.
 */
@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "Demo", description = "Demo endpoints")
@SecurityRequirement(name = "Authorization")
public class DemoController {
    @Operation(
            summary = "Say hello",
            description = "This endpoint returns a simple hello message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hello message"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized")
            }
    )
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from secured endpoint";
    }

    /**
     * The @Hidden annotation is used to hide an operation from the API documentation.
     */
    @Hidden
    @GetMapping("/bye")
    public ResponseEntity<String> sayBye() {
        return ResponseEntity.ok("Bye from secured endpoint");
    }
}
