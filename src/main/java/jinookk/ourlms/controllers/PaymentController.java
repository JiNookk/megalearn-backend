package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.KakaoRequestDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.KakaoService;
import jinookk.ourlms.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final KakaoService kakaoService;

    public PaymentController(PaymentService paymentService, KakaoService kakaoService) {
        this.paymentService = paymentService;
        this.kakaoService = kakaoService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/payments/kakao-ready")
    public KakaoReadyDto paymentReady(
            @RequestAttribute Long accountId,
            @RequestBody KakaoRequestDto kakaoRequestDto
    ) {
        return kakaoService.paymentUrl(new AccountId(accountId), kakaoRequestDto.getCourseIds());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/payments")
    public PaymentsDto purchase(
            @RequestAttribute Long accountId,
            @RequestBody PaymentRequestDto paymentRequestDto
    ) {
        return paymentService.purchase(paymentRequestDto, new AccountId(accountId));
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
