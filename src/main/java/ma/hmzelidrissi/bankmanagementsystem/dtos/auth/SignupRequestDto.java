package ma.hmzelidrissi.bankmanagementsystem.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequestDto(
        @NotNull(message = "name is required")
        String name,

        @NotNull(message = "email is required")
        @Email(message = "email should be valid")
        String email,

        @NotNull(message = "password is required")
        @Size(min = 6, message = "password should be at least 6 characters")
        String password
) {
}
