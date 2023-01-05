package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void list() throws Exception {
        PaymentDto paymentDto = Payment.fake(35_000).toDto();

        given(paymentService.list(new AccountId(1L), new CourseId(1L)))
                .willReturn(new PaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/payments?courseId=1")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"payments\":[")
                ));
    }

    @Test
    void monthlyList() throws Exception {
        MonthlyPaymentDto paymentDto = new MonthlyPaymentDto(new CourseId(1L), new Title("test"), 35_000);

        given(paymentService.monthlyList(new AccountId(1L)))
                .willReturn(new MonthlyPaymentsDto(List.of(paymentDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/monthly-total-payments")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"monthlyPayments\":[")
                ));
    }
}
