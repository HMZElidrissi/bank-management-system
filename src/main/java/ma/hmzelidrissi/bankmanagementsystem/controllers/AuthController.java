package ma.hmzelidrissi.bankmanagementsystem.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.AuthenticationResponseDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SigninRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.dtos.auth.SignupRequestDto;
import ma.hmzelidrissi.bankmanagementsystem.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
  public AuthenticationResponseDto signup(
      @Valid @RequestBody SignupRequestDto request, HttpServletResponse response) {
    return authService.signup(request, response);
  }

  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  public AuthenticationResponseDto signin(
      @Valid @RequestBody SigninRequestDto request, HttpServletResponse response) {
    return authService.signin(request, response);
  }

  @PostMapping("/signout")
  @ResponseStatus(HttpStatus.OK)
  public void signout(HttpServletResponse response) {
    authService.signout(response);
  }
}
