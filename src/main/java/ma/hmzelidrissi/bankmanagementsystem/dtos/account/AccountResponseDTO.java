package ma.hmzelidrissi.bankmanagementsystem.dtos.account;

import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private double balance;
    private AccountStatus status;
}
