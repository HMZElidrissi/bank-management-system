package ma.hmzelidrissi.bankmanagementsystem.dtos.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;              // HTTP status code
    private String error;            // HTTP status text (e.g., "Not Found", "Bad Request")
    private String message;          // Detailed error message
    private String path;             // API endpoint where error occurred
    private Map<String, String> validationErrors;  // Field-specific validation errors
}