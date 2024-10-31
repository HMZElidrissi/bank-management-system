package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;

public interface AuthService {
    AuthenticationResponseDto signup(SignupRequestDto request);

    AuthenticationResponseDto signin(SigninRequestDto request);
}
