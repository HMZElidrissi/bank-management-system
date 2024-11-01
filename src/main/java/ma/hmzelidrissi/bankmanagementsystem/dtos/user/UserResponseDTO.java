package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private double monthlyIncome;
    private int creditScore;
    private Role role;
}
