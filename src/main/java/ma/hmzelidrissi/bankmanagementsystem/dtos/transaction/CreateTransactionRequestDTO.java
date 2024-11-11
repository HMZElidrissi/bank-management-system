package ma.hmzelidrissi.bankmanagementsystem.dtos.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionFrequency;

import java.time.LocalDateTime;

public record CreateTransactionRequestDTO(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        Double amount,

        @NotNull(message = "Source account ID is required")
        Long sourceAccountId,

        Long destinationAccountId,
        String description,
        boolean instant,
        boolean external,

        // For recurring transactions
        boolean recurring,
        TransactionFrequency frequency,
        LocalDateTime endDate,
        Integer totalExecutions
) {
}
