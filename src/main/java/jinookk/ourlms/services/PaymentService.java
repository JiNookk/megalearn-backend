package jinookk.ourlms.services;

import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final KakaoService kakaoService;

    public PaymentService(PaymentRepository paymentRepository,
                          CartRepository cartRepository,
                          CourseRepository courseRepository,
                          AccountRepository accountRepository,
                          KakaoService kakaoService) {
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.kakaoService = kakaoService;
    }

    public PaymentsDto list(AccountId accountId, CourseId courseId) {
        List<Course> courses = getCourses(accountId, courseId);

        List<PaymentDto> paymentDtos = courses.stream()
                .map(course -> paymentRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    public MonthlyPaymentsDto monthlyList(AccountId accountId) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<MonthlyPaymentDto> monthlyPaymentDtos = courses.stream()
                .map(course -> course.toMonthlyPaymentDto(
                        paymentRepository.findAllByCourseId(new CourseId(course.id()))))
                .toList();

        return new MonthlyPaymentsDto(monthlyPaymentDtos);
    }

    private List<Course> getCourses(AccountId accountId, CourseId courseId) {
        if (courseId.value() == null || courseId.value() < 1) {
            return courseRepository.findAllByAccountId(accountId);
        }

        return courseRepository.findAllByAccountId(accountId).stream()
                .filter(course -> course.filterId(courseId))
                .toList();
    }

    public PaymentsDto purchase(PaymentRequestDto paymentRequestDto, AccountId accountId) {
        KakaoPayItemVO kakaoPayItemVO = kakaoService.approve(paymentRequestDto, accountId);

        Account account = accountRepository.findById(accountId.value())
                .orElseThrow(() -> new AccountNotFound(accountId));

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        List<Payment> payments = Payment.listOf(kakaoPayItemVO.getCourses(), account, cart);

        List<Payment> saved = paymentRepository.saveAll(payments);

        List<PaymentDto> paymentDtos = saved.stream()
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }
}
