package ma.hmzelidrissi.bankmanagementsystem.dtos.account;

import jakarta.validation.constraints.NotNull;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;

public record UpdateAccountRequestDTO(
        @NotNull(message = "Amount is required")
        double amount,

        @NotNull(message = "Status is required")
        AccountStatus status
) {
}
