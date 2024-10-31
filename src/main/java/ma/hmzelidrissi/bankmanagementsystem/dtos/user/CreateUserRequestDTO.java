package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @PositiveOrZero(message = "Monthly income must be positive or zero")
    private double monthlyIncome;

    @Min(value = 300, message = "Credit score must be at least 300")
    @Max(value = 850, message = "Credit score cannot exceed 850")
    private int creditScore;

    @NotNull(message = "Role is required")
    private Role role;
}
