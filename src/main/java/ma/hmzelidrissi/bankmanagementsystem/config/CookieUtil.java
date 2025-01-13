package ma.hmzelidrissi.bankmanagementsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class CookieUtil {
  @Value("${application.jwt.cookie.name:jwt-token}")
  private String cookieName;

  @Value("${application.jwt.cookie.expiry:86400}")
  private int cookieExpiry;

  @Value("${application.jwt.cookie.secure:true}")
  private boolean isSecure;

  @Value("${application.jwt.cookie.same-site:None}")
  private String sameSite;

  @Value("${application.jwt.cookie.domain:localhost}")
  private String domain;

  public void createCookie(HttpServletResponse response, String token) {
    ResponseCookie cookie =
        ResponseCookie.from(cookieName, token)
            .httpOnly(true)
            .secure(isSecure)
            .path("/")
            .domain(domain)
            .maxAge(Duration.ofSeconds(cookieExpiry))
            .sameSite(sameSite)
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public void clearCookie(HttpServletResponse response) {
    ResponseCookie cookie =
        ResponseCookie.from(cookieName, "")
            .httpOnly(true)
            .secure(isSecure)
            .path("/")
            .domain(domain)
            .maxAge(0)
            .sameSite(sameSite)
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public String extractToken(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return null;
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookieName.equals(cookie.getName()))
        .map(jakarta.servlet.http.Cookie::getValue)
        .findFirst()
        .orElse(null);
  }
}
