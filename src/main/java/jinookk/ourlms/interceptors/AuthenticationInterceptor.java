package jinookk.ourlms.interceptors;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jinookk.ourlms.exceptions.AccessTokenExpired;
import jinookk.ourlms.exceptions.AuthenticationError;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.utils.HttpUtil;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor{
    private final JwtUtil jwtUtil;
    private final HttpUtil httpUtil;

    public AuthenticationInterceptor(JwtUtil jwtUtil, HttpUtil httpUtil) {
        this.jwtUtil = jwtUtil;
        this.httpUtil = httpUtil;
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
            setUsernameAttribute(request, accessToken);

            return true;
        } catch (JWTDecodeException e) {
            throw new AuthenticationError(accessToken);
        } catch (TokenExpiredException e) {
            throw new AccessTokenExpired();
        }
    }

    private void setUsernameAttribute(HttpServletRequest request, String token) {
        Name userName = jwtUtil.decode(token);
        request.setAttribute("userName", userName);
    }
}
