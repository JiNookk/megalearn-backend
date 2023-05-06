package jinookk.ourlms.applications.payment;

import jinookk.ourlms.applications.kakao.KakaoPayService;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.enums.PaymentStatus;
import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreatePaymentServiceTest extends Fixture {
    CreatePaymentService createPaymentService;
    KakaoPayService kakaoPayService;
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
        kakaoPayService = mock(KakaoPayService.class);
        createPaymentService = new CreatePaymentService(
                paymentRepository, cartRepository, lectureRepository,
                accountRepository, progressRepository,kakaoPayService, courseRepository);

        Course course1 = new Course(1L, new Title("courseTitle1"), new Content("description"),
                CourseStatus.APPROVED, new ImagePath("imagePath"), new Category("category"),
                new Name("instructor1", false), new AccountId(5L), new Price(35_000));

        Course course2 = new Course(2L, new Title("courseTitle2"), new Content("description"),
                CourseStatus.APPROVED, new ImagePath("imagePath"), new Category("category"),
                new Name("instructor2", false), new AccountId(5L), new Price(24_000));

        Course course3 = new Course(3L, new Title("courseTitle3"), new Content("description"),
                CourseStatus.APPROVED, new ImagePath("imagePath"), new Category("category"),
                new Name("instructor3", false), new AccountId(5L), new Price(49_000));

        given(courseRepository.findAllByAccountId(new AccountId(1L)))
                .willReturn(List.of(course1, course2, course3));

        given(paymentRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(
                        new Payment(1L, new CourseId(1L), new AccountId(1L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS,
                                LocalDateTime.now()),
                        new Payment(2L, new CourseId(1L), new AccountId(2L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS,
                                LocalDateTime.now()),
                        new Payment(3L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                                new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS,
                                LocalDateTime.now())
                ));

        given(paymentRepository.findAllByCourseId(new CourseId(2L)))
                .willReturn(List.of(
                        new Payment(4L, new CourseId(2L), new AccountId(1L), new Price(24_000),
                                new Title("courseTitle2"), new Name("purchaser", false), PaymentStatus.SUCCESS,
                                LocalDateTime.now())
                ));

        given(paymentRepository.findAllByCourseId(new CourseId(3L)))
                .willReturn(List.of(
                        new Payment(5L, new CourseId(3L), new AccountId(2L), new Price(49_000),
                                new Title("courseTitle3"), new Name("purchaser", false), PaymentStatus.SUCCESS,
                                LocalDateTime.now())
                ));


        given(paymentRepository.findAllByAccountId(new AccountId(any())))
                .willReturn(List.of(Fixture.payment(24000), Fixture.payment(35000), Fixture.payment(49000)));

        Account account = Fixture.account("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

//    @Test
//    void purchase() {
//        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("TOKEN");
//        AccountId accountId = new AccountId(1L);
//
//        Course course = Fixture.course("hi");
//        Account account = Fixture.account("account");
//        Cart cart = Fixture.cart(new AccountId(1L));
//
//        KakaoPayItemVO kakaoPayItemVO = new KakaoPayItemVO(account, List.of(course), cart);
//
//        given(kakaoPayService.approve(paymentRequestDto, accountId))
//                .willReturn(kakaoPayItemVO);
//
//
//        given(accountRepository.findById(accountId.value()))
//                .willReturn(Optional.of(account));
//
//        Payment payment = Fixture.payment(35000);
//        given(paymentRepository.saveAll(any())).willReturn(List.of(payment));
//
//
//        cart = cart.addItem(course.id());
//
//        given(cartRepository.findByAccountId(new AccountId(1L))).willReturn(Optional.of(cart));
//
//        PaymentsDto paymentsDto = createPaymentService.purchase(paymentRequestDto, new UserName("userName@email.com"));
//
//        assertThat(paymentsDto.getPayments()).hasSize(1);
//        assertThat(cart.itemIds()).hasSize(0);
//    }
}
