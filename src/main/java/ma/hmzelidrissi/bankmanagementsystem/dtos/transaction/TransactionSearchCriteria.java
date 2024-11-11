package ma.hmzelidrissi.bankmanagementsystem.dtos.transaction;

import lombok.Builder;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionStatus;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionType;

import java.time.LocalDateTime;

@Builder
public record TransactionSearchCriteria(
        Long accountId,
        TransactionType type,
        TransactionStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double minAmount,
        Double maxAmount,
        Boolean requiresApproval,
        Boolean recurring
) {
}
