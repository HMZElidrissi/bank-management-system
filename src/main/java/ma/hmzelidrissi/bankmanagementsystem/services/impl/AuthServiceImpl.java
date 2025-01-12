package ma.hmzelidrissi.bankmanagementsystem.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.http.HttpServletResponse;
import ma.hmzelidrissi.bankmanagementsystem.config.CookieUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;
import ma.hmzelidrissi.bankmanagementsystem.repositories.UserRepository;
import ma.hmzelidrissi.bankmanagementsystem.services.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final CookieUtil cookieUtil;

    private String generateToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("bank-management-system")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.DAYS))
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    @Override
    public AuthenticationResponseDto signup(SignupRequestDto request, HttpServletResponse response) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = generateToken(user);
        cookieUtil.createCookie(response, jwtToken);

        return AuthenticationResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public AuthenticationResponseDto signin(SigninRequestDto request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        var jwtToken = generateToken(user);
        cookieUtil.createCookie(response, jwtToken);

        return AuthenticationResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public void signout(HttpServletResponse response) {
        cookieUtil.clearCookie(response);
    }
}
