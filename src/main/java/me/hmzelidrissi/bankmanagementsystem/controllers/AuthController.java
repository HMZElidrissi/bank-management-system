package me.hmzelidrissi.bankmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import me.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import me.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;
import me.hmzelidrissi.bankmanagementsystem.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponseDto> signup(
            @Valid @RequestBody SignupRequestDto request
    ) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponseDto> signin(
            @Valid @RequestBody SigninRequestDto request
    ) {
        return ResponseEntity.ok(authService.signin(request));
    }
}
