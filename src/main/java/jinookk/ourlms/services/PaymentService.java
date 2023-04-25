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
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LectureRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final ProgressRepository progressRepository;
    private final AccountRepository accountRepository;
    private final KakaoService kakaoService;

    public PaymentService(PaymentRepository paymentRepository,
                          CartRepository cartRepository,
                          CourseRepository courseRepository,
                          LectureRepository lectureRepository,
                          AccountRepository accountRepository,
                          ProgressRepository progressRepository,
                          KakaoService kakaoService) {
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
        this.accountRepository = accountRepository;
        this.progressRepository = progressRepository;
        this.kakaoService = kakaoService;
    }

    public PaymentsDto list() {
        return new PaymentsDto(paymentRepository.findAll().stream()
                .map(Payment::toDto)
                .toList());
    }

    public PaymentsDto list(UserName userName, CourseId courseId) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = getCourses(accountId, courseId);

        List<PaymentDto> paymentDtos = courses.stream()
                .map(course -> paymentRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    public MonthlyPaymentsDto monthlyList(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<MonthlyPaymentDto> monthlyPaymentDtos = courses.stream()
                .map(course -> course.toMonthlyPaymentDto(
                        paymentRepository.findAllByCourseId(new CourseId(course.id()))))
                .toList();

        return new MonthlyPaymentsDto(monthlyPaymentDtos);
    }

    public PaymentsDto purchase(PaymentRequestDto paymentRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        KakaoPayItemVO kakaoPayItemVO = kakaoService.approve(paymentRequestDto, accountId);

        List<Lecture> lectures = kakaoPayItemVO.getCourses().stream()
                .map(course -> lectureRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .toList();

        List<Payment> payments = Payment.listOf(kakaoPayItemVO.getCourses(), account, cart);

        List<Progress> progresses = Progress.listOf(lectures, new AccountId(account.id()));

        progressRepository.saveAll(progresses);

        List<Payment> saved = paymentRepository.saveAll(payments);

        List<PaymentDto> paymentDtos = saved.stream()
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    public PaymentsDto list(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Payment> payments = paymentRepository.findAllByAccountId(accountId);

        List<PaymentDto> paymentDtos = payments.stream()
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    private List<Course> getCourses(AccountId accountId, CourseId courseId) {
        if (courseId.value() == null || courseId.value() < 1) {
            return courseRepository.findAllByAccountId(accountId);
        }

        return courseRepository.findAllByAccountId(accountId).stream()
                .filter(course -> course.filterId(courseId))
                .toList();
    }
}
