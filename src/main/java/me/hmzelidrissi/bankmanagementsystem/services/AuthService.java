package me.hmzelidrissi.bankmanagementsystem.services;

import me.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import me.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import me.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;

public interface AuthService {

    AuthenticationResponseDto signup(SignupRequestDto request);

    AuthenticationResponseDto signin(SigninRequestDto request);
}
