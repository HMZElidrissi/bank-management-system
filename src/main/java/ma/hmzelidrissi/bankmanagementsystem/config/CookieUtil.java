package ma.hmzelidrissi.bankmanagementsystem.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    @Value("${application.jwt.cookie.name:jwt-token}")
    private String cookieName;

    @Value("${application.jwt.cookie.expiry:86400}")
    private int cookieExpiry;

    public void createCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); # Uncomment this line when deploying to production
        cookie.setPath("/");
        cookie.setMaxAge(cookieExpiry);
        response.addCookie(cookie);
    }

    public void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
