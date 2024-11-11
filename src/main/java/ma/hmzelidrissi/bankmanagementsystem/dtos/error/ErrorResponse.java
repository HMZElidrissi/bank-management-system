package ma.hmzelidrissi.bankmanagementsystem.dtos.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // see https://www.baeldung.com/spring-remove-null-objects-json-response-jackson
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,                                 // HTTP status code
        String error,                               // HTTP status text (e.g., "Not Found", "Bad Request")
        String message,                             // Detailed error message
        String path,                                // API endpoint where error occurred
        Map<String, String> validationErrors        // Field-specific validation errors
) {
}