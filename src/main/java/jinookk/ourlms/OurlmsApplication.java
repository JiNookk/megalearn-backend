package jinookk.ourlms;

import jinookk.ourlms.interceptors.AuthenticationInterceptor;
import jinookk.ourlms.utils.HttpUtil;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class OurlmsApplication {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${cors.allowed_userOrigin}")
    private String userOrigin;

    @Value("${cors.allowed_adminOrigin}")
    private String adminOrigin;

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB");
        SpringApplication.run(OurlmsApplication.class, args);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authenticationInterceptor());
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(userOrigin, adminOrigin)
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }

    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(jwtUtil(), httpUtil());
    }

    @Bean
    public HttpUtil httpUtil() {
        return new HttpUtil();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
