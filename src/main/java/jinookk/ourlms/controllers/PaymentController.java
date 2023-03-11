package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.KakaoRequestDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.vos.Name;
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
            @RequestAttribute Name userName,
            @RequestBody KakaoRequestDto kakaoRequestDto
    ) {
        return kakaoService.paymentUrl(userName, kakaoRequestDto.getCourseIds());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/payments")
    public PaymentsDto purchase(
            @RequestAttribute Name userName,
            @RequestBody PaymentRequestDto paymentRequestDto
    ) {
        return paymentService.purchase(paymentRequestDto, userName);
    }

    @GetMapping("/payments")
    public PaymentsDto detail() {
        return paymentService.list();
    }

    @GetMapping("/instructor/payments")
    public PaymentsDto list(
            @RequestAttribute Name userName,
            @RequestParam(required = false) Long courseId
    ) {
        return paymentService.list(userName, new CourseId(courseId));
    }

    @GetMapping("/payments/me")
    public PaymentsDto list(
            @RequestAttribute Name userName
    ) {
        return paymentService.list(userName);
    }

    @GetMapping("/instructor/monthly-total-payments")
    public MonthlyPaymentsDto monthlyList(
            @RequestAttribute Name userName
    ) {
        return paymentService.monthlyList(userName);
    }
}
