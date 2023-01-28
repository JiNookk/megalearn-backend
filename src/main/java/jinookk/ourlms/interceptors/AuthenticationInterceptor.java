package jinookk.ourlms.interceptors;

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

        Name userName = jwtUtil.decode(accessToken);

        request.setAttribute("userName", userName);
        System.out.println(userName);

//        if (accessToken.equals("ACCESS.TOKEN")) {
//            request.setAttribute("accountId", 1L);
//        }
//
//        if (accessToken.equals("ACCESS.TOKEN2")) {
//            request.setAttribute("accountId", 2L);
//        }
//
//        if (accessToken.equals("ACCESS.TOKEN3")) {
//            request.setAttribute("accountId", 3L);
//        }
//

        return true;
    }
}
