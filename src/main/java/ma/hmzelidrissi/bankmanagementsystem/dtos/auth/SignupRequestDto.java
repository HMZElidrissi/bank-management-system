package ma.hmzelidrissi.bankmanagementsystem.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

    @NotNull(message = "password is required")
    @Size(min = 6, message = "password should be at least 6 characters")
    private String password;
}
