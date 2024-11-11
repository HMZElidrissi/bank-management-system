package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import jakarta.validation.constraints.*;

public record UpdateUserRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @Min(value = 18, message = "Age must be at least 18")
        int age,

        @PositiveOrZero(message = "Monthly income must be positive or zero")
        double monthlyIncome,

        @Min(value = 300, message = "Credit score must be at least 300")
        @Max(value = 850, message = "Credit score cannot exceed 850")
        int creditScore
) {
}
