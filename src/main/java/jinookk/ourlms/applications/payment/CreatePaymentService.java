package jinookk.ourlms.applications.payment;

import jinookk.ourlms.applications.kakao.KakaoService;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.LectureRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final KakaoService kakaoService;

    public CreatePaymentService(PaymentRepository paymentRepository,
                                CartRepository cartRepository,
                                LectureRepository lectureRepository,
                                AccountRepository accountRepository,
                                ProgressRepository progressRepository,
                                KakaoService kakaoService) {
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.lectureRepository = lectureRepository;
        this.accountRepository = accountRepository;
        this.progressRepository = progressRepository;
        this.kakaoService = kakaoService;
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
}
