package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.kakao.KakaoPayService;
import jinookk.ourlms.applications.payment.CreatePaymentService;
import jinookk.ourlms.applications.payment.GetPaymentService;
import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPaymentService getPaymentService;

    @MockBean
    private CreatePaymentService createPaymentService;

    @MockBean
    private KakaoPayService kakaoPayService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void kakaoReady() throws Exception {
        given(kakaoPayService.paymentUrl(any(), any()))
                .willReturn(new KakaoReadyDto("http://localhost:8080"));

        mockMvc.perform(MockMvcRequestBuilders.post("/payments/kakao-ready")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"courseId\":1" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"paymentUrl\":\"http://localhost:8080\"")
                ));
    }

    @Test
    void purchase() throws Exception {
        PaymentDto paymentDto = Fixture.payment(35_000).toDto();

        given(createPaymentService.purchase(any(), any()))
                .willReturn(new PaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"pgToken\":\"TOKEN\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"payments\":[")
                ));
    }

    @Test
    void list() throws Exception {
        PaymentDto paymentDto = Fixture.payment(35_000).toDto();

        given(getPaymentService.list(new UserName("userName@email.com"), new CourseId(1L)))
                .willReturn(new PaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/payments?courseId=1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"payments\":[")
                ));
    }

    @Test
    void myPayments() throws Exception {
        PaymentDto paymentDto = Fixture.payment(35_000).toDto();

        given(getPaymentService.list(new UserName("userName@email.com")))
                .willReturn(new PaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/payments/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"payments\":[")
                ));
    }

    @Test
    void monthlyList() throws Exception {
        MonthlyPaymentDto paymentDto = new MonthlyPaymentDto(new CourseId(1L), new Title("test"), 35_000);

        given(getPaymentService.monthlyList(new UserName("userName@email.com")))
                .willReturn(new MonthlyPaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/monthly-total-payments")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"monthlyPayments\":[")
                ));
    }
}
