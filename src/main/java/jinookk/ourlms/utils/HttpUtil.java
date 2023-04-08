package jinookk.ourlms.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    public ResponseCookie generateHttpOnlyCookie(String cookieName, String cookieValue) {
        String encodedCookieValue = URLEncoder.encode(cookieValue, StandardCharsets.UTF_8);

        return ResponseCookie.from(cookieName, encodedCookieValue)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
    }

    public HttpServletResponse addCookie(ResponseCookie cookie, HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return response;
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
