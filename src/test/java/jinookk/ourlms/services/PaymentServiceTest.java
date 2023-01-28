package jinookk.ourlms.services;

import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LectureRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {
    PaymentService paymentService;
    KakaoService kakaoService;
    PaymentRepository paymentRepository;
    LectureRepository lectureRepository;
    ProgressRepository progressRepository;
    CourseRepository courseRepository;
    AccountRepository accountRepository;
    CartRepository cartRepository;

    @BeforeEach
    void setup() {
        lectureRepository = mock(LectureRepository.class);
        progressRepository = mock(ProgressRepository.class);
        cartRepository = mock(CartRepository.class);
        courseRepository = mock(CourseRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        accountRepository = mock(AccountRepository.class);
        kakaoService = mock(KakaoService.class);
        paymentService = new PaymentService(
                paymentRepository, cartRepository, courseRepository, lectureRepository, accountRepository,
                progressRepository,kakaoService);

        Course course1 = new Course(1L, new Title("courseTitle1"), new Content("description"),
                new Status(Status.APPROVED), new ImagePath("imagePath"), new Category("category"),
                new Name("instructor1"), new AccountId(5L), new Price(35_000));

        Course course2 = new Course(2L, new Title("courseTitle2"), new Content("description"),
                new Status(Status.APPROVED), new ImagePath("imagePath"), new Category("category"),
                new Name("instructor2"), new AccountId(5L), new Price(24_000));

        Course course3 = new Course(3L, new Title("courseTitle3"), new Content("description"),
                new Status(Status.APPROVED), new ImagePath("imagePath"), new Category("category"),
                new Name("instructor3"), new AccountId(5L), new Price(49_000));

        given(courseRepository.findAllByAccountId(new AccountId(1L)))
                .willReturn(List.of(course1, course2, course3));

        given(paymentRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(
                        new Payment(1L, new CourseId(1L), new AccountId(1L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now()),
                        new Payment(2L, new CourseId(1L), new AccountId(2L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now()),
                        new Payment(3L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now())
                ));

        given(paymentRepository.findAllByCourseId(new CourseId(2L)))
                .willReturn(List.of(
                        new Payment(4L, new CourseId(2L), new AccountId(1L), new Price(24_000),
                                new Title("courseTitle2"), new Name("purchaser"), LocalDateTime.now())
                ));

        given(paymentRepository.findAllByCourseId(new CourseId(3L)))
                .willReturn(List.of(
                        new Payment(5L, new CourseId(3L), new AccountId(2L), new Price(49_000),
                                new Title("courseTitle3"), new Name("purchaser"), LocalDateTime.now())
                ));


        given(paymentRepository.findAllByAccountId(new AccountId(any())))
                .willReturn(List.of(Payment.fake(24000), Payment.fake(35000), Payment.fake(49000)));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void purchase() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("TOKEN");
        AccountId accountId = new AccountId(1L);

        Course course = Course.fake("hi");

        KakaoPayItemVO kakaoPayItemVO = new KakaoPayItemVO(List.of(course));

        given(kakaoService.approve(paymentRequestDto, accountId))
                .willReturn(kakaoPayItemVO);

        Account account = Account.fake("account");

        given(accountRepository.findById(accountId.value()))
                .willReturn(Optional.of(account));

        Payment payment = Payment.fake(35000);
        given(paymentRepository.saveAll(any())).willReturn(List.of(payment));

        Cart cart = Cart.fake(new AccountId(1L));

        cart = cart.addItem(course.id());

        given(cartRepository.findByAccountId(new AccountId(1L))).willReturn(Optional.of(cart));

        PaymentsDto paymentsDto = paymentService.purchase(paymentRequestDto, new Name("userName"));

        assertThat(paymentsDto.getPayments()).hasSize(1);
        assertThat(cart.itemIds()).hasSize(0);
    }

    @Test
    void list() {
        PaymentsDto paymentsDto = paymentService.list(new Name("userName"), new CourseId(1L));

        assertThat(paymentsDto.getPayments()).hasSize(3);
    }

    @Test
    void myPayments() {
        PaymentsDto paymentsDto = paymentService.list(new Name("userName"));

        assertThat(paymentsDto.getPayments()).hasSize(3);
    }

    @Test
    void monthlyList() {
        MonthlyPaymentsDto monthlyPaymentsDto = paymentService.monthlyList(new Name("userName"));

        assertThat(monthlyPaymentsDto.getMonthlyPayments()).hasSize(3);
    }
}
