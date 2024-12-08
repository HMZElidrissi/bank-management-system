package ma.hmzelidrissi.bankmanagementsystem.services;

import jakarta.servlet.http.HttpServletResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;

public interface AuthService {
    AuthenticationResponseDto signup(SignupRequestDto request, HttpServletResponse response);

    AuthenticationResponseDto signin(SigninRequestDto request, HttpServletResponse response);

    void signout(HttpServletResponse response);
}
