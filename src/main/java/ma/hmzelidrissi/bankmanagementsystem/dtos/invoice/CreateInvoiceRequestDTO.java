package ma.hmzelidrissi.bankmanagementsystem.dtos.invoice;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateInvoiceRequestDTO(
        @NotNull(message = "Invoice amount is required")
        @Positive(message = "Invoice amount must be positive")
        double amount,

        @NotNull(message = "Invoice due date is required")
        @Future(message = "Due date must be in the future")
        LocalDate dueDate,

        @NotNull(message = "Description is required")
        @Size(min = 3, max = 255)
        String description,

        @NotNull(message = "Account ID is required")
        Long accountId
) {
}
