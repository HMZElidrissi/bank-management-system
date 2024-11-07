package ma.hmzelidrissi.bankmanagementsystem.dtos.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateAccountRequestDTO(
        @NotNull(message = "User ID is required")
        Long userId,
        @PositiveOrZero(message = "Initial balance must be zero or positive")
        double initialBalance
) {
}
