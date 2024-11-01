package ma.hmzelidrissi.bankmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.config.JWTService;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;
import ma.hmzelidrissi.bankmanagementsystem.services.AuthService;
import ma.hmzelidrissi.bankmanagementsystem.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto signup(SignupRequestDto request) {
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
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public AuthenticationResponseDto signin(SigninRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }
}
