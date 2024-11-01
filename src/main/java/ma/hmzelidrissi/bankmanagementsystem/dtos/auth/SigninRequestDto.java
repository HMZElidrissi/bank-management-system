package ma.hmzelidrissi.bankmanagementsystem.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SigninRequestDto(
        @NotNull(message = "email is required")
        @Email(message = "email should be valid")
        String email,

        @NotNull(message = "password is required")
        String password
) {
}
