package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/instructor/payments")
    public PaymentsDto list(
            @RequestAttribute Long accountId,
            @RequestParam(required = false) Long courseId
    ) {
        return paymentService.list(new AccountId(accountId), new CourseId(courseId));
    }

    @GetMapping("/instructor/monthly-total-payments")
    public MonthlyPaymentsDto monthlyList(
            @RequestAttribute Long accountId
    ) {
        return paymentService.monthlyList(new AccountId(accountId));
    }
}
