package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;

@Data
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
