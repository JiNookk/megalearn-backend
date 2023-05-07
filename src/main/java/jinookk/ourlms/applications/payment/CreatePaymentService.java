package jinookk.ourlms.applications.payment;

import jinookk.ourlms.applications.kakao.KakaoPayService;
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
import jinookk.ourlms.models.enums.PaymentStatus;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class CreatePaymentService {
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final LectureRepository lectureRepository;
    private final ProgressRepository progressRepository;
    private final AccountRepository accountRepository;
    private final KakaoPayService kakaoPayService;
    private final CourseRepository courseRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public CreatePaymentService(PaymentRepository paymentRepository,
                                CartRepository cartRepository,
                                LectureRepository lectureRepository,
                                AccountRepository accountRepository,
                                ProgressRepository progressRepository,
                                KakaoPayService kakaoPayService, CourseRepository courseRepository) {
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.lectureRepository = lectureRepository;
        this.accountRepository = accountRepository;
        this.progressRepository = progressRepository;
        this.kakaoPayService = kakaoPayService;
        this.courseRepository = courseRepository;
    }

    public PaymentsDto purchase(PaymentRequestDto paymentRequestDto, UserName userName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        KakaoPayItemVO kakaoPayItemVO = kakaoPayService.approve(paymentRequestDto, accountId);

        List<Long> courseIds = kakaoPayItemVO.getCourses().stream().map(Course::id).toList();

        List<Course> courses = courseRepository.findAllById(courseIds);

        transaction.begin();

        try {
            List<Lecture> lectures = kakaoPayItemVO.getCourses().stream()
                    .map(course -> lectureRepository.findAllByCourseId(new CourseId(course.id())))
                    .flatMap(Collection::stream)
                    .toList();

            List<Payment> payments = Payment.listOf(courses, account, cart, PaymentStatus.SUCCESS);

            List<Progress> progresses = Progress.listOf(lectures, new AccountId(account.id()));

            progressRepository.saveAll(progresses);

            List<Payment> saved = paymentRepository.saveAll(payments);

            List<PaymentDto> paymentDtos = saved.stream()
                    .map(Payment::toDto)
                    .toList();

            transaction.commit();

            return new PaymentsDto(paymentDtos);

        } catch (RuntimeException exception){
            transaction.rollback();

            List<Payment> payments = Payment.listOf(courses, account, cart, PaymentStatus.FAILED);
            paymentRepository.saveAll(payments);

            throw exception;
        }
    }
}
