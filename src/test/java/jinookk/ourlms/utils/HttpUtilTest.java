package jinookk.ourlms.utils;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HttpUtilTest {
    HttpUtil httpUtil;

    @BeforeEach
    void setup() {
        httpUtil = new HttpUtil();
    }

    @Test
    void generateHttpOnlyCookie() {
        ResponseCookie cookie = httpUtil.generateHttpOnlyCookie("cookieName", "cookieValue");

        assertThat(cookie.isHttpOnly()).isTrue();
        assertThat(cookie.getName()).isEqualTo("cookieName");
        assertThat(cookie.getValue()).isEqualTo("cookieValue");
    }
}
