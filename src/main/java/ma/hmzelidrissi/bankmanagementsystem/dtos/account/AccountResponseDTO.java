package ma.hmzelidrissi.bankmanagementsystem.dtos.account;

import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;

@Builder
public record AccountResponseDTO(
        Long id,
        Long userId,
        String userName,
        String userEmail,
        double balance,
        AccountStatus status
) {
}
