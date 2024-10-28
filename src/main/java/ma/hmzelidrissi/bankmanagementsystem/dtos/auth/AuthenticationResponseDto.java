package ma.hmzelidrissi.bankmanagementsystem.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String name;
    private String email;
    private String role;
    private String token;
    private String profilePicture;
}
