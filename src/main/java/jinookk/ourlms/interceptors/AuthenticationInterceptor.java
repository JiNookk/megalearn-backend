package jinookk.ourlms.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return true;
        }

        String accessToken = authorization.substring("Bearer ".length());

        if (accessToken.equals("ACCESS.TOKEN")) {
            request.setAttribute("accountId", 1L);
        }

        if (accessToken.equals("ACCESS.TOKEN2")) {
            request.setAttribute("accountId", 2L);
        }

        if (accessToken.equals("ACCESS.TOKEN3")) {
            request.setAttribute("accountId", 3L);
        }

        System.out.println(accessToken);

        return true;
    }
}
