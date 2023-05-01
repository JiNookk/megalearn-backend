package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.payment.CreatePaymentService;
import jinookk.ourlms.applications.payment.GetPaymentService;
import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.KakaoRequestDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.applications.kakao.KakaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final GetPaymentService getPaymentService;
    private final CreatePaymentService createPaymentService;
    private final KakaoService kakaoService;

    public PaymentController(GetPaymentService getPaymentService, CreatePaymentService createPaymentService, KakaoService kakaoService) {
        this.getPaymentService = getPaymentService;
        this.createPaymentService = createPaymentService;
        this.kakaoService = kakaoService;
    }


    @PostMapping("/payments/kakao-ready")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Payment Ready", notes = "request ready phase to Kakao Server")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public KakaoReadyDto paymentReady(
            @RequestAttribute UserName userName,
            @RequestBody KakaoRequestDto kakaoRequestDto
    ) {
        return kakaoService.paymentUrl(userName, kakaoRequestDto.getCourseIds());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/payments")
    @ApiOperation(value = "Purchase", notes = "create Payment Entity")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public PaymentsDto purchase(
            @RequestAttribute UserName userName,
            @RequestBody PaymentRequestDto paymentRequestDto
    ) {
        return createPaymentService.purchase(paymentRequestDto, userName);
    }

    @GetMapping("/payments")
    public PaymentsDto detail() {
        return getPaymentService.list();
    }

    @GetMapping("/instructor/payments")
//    @ApiOperation(value = "Payment List", notes = "fetches payments with instructor ")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
//    })
    public PaymentsDto list(
            @RequestAttribute UserName userName,
            @RequestParam(required = false) Long courseId
    ) {
        return getPaymentService.list(userName, new CourseId(courseId));
    }

    @GetMapping("/payments/me")
    @ApiOperation(value = "My Payment List", notes = "fetches my payments list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public PaymentsDto list(
            @RequestAttribute UserName userName
    ) {
        return getPaymentService.list(userName);
    }

    @GetMapping("/instructor/monthly-total-payments")
    @ApiOperation(value = "Monthly payments", notes = "fetches instructor's monthly earned")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public MonthlyPaymentsDto monthlyList(
            @RequestAttribute UserName userName
    ) {
        return getPaymentService.monthlyList(userName);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
