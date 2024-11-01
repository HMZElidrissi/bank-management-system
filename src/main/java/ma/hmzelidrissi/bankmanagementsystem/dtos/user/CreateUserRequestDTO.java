package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import jakarta.validation.constraints.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;

public record CreateUserRequestDTO(
        @NotBlank(message = "Name is required") String name,

        @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") @Size(min = 6, message = "Password should be at least 6 characters")
        String password,

        @Min(value = 18, message = "Age must be at least 18")
        int age,

        @PositiveOrZero(message = "Monthly income must be positive or zero")
        double monthlyIncome,

        @Min(value = 300, message = "Credit score must be at least 300")
        @Max(value = 850, message = "Credit score cannot exceed 850")
        int creditScore,

        @NotNull(message = "Role is required")
        Role role
) {
}
