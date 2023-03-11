package jinookk.ourlms.interceptors;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jinookk.ourlms.exceptions.AuthenticationError;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor{
    private final JwtUtil jwtUtil;

    public AuthenticationInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");


        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return true;
        }

        String accessToken = authorization.substring("Bearer ".length());

        try {
            Name userName = jwtUtil.decode(accessToken);
            request.setAttribute("userName", userName);

            return true;
        } catch (JWTDecodeException e) {
            throw new AuthenticationError(accessToken);
        }
    }
}
