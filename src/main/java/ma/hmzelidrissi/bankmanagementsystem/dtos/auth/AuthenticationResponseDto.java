package ma.hmzelidrissi.bankmanagementsystem.dtos.auth;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String name;
    private String email;
    private String role;
    private String token;
    private String profilePicture;
}
