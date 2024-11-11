package ma.hmzelidrissi.bankmanagementsystem.dtos.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TransactionApprovalRequestDTO(
        @NotNull(message = "Approval decision is required")
        boolean approved,
        String comment
) {
}
