package jinookk.ourlms.applications.kakao;

import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.exceptions.KakaoApprovalFail;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.enums.PaymentStatus;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.kakao.KakaoPayApprovalVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayReadyVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KakaoPayService {
    private static final String HOST = "https://kapi.kakao.com";

    @Value("${kakao.admin-key}")
    private String adminKey;

    @Value("${kakao.approval.url}")
    private String approvalUrl;

    @Value("${kakao.fail.url}")
    private String failUrl;

    @Value("${kakao.cancel.url}")
    private String cancelUrl;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private KakaoPayItemVO kakaoPayItemVO;

    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;

    public KakaoPayService(CourseRepository courseRepository,
                           AccountRepository accountRepository,
                           CartRepository cartRepository,
                           PaymentRepository paymentRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
        this.paymentRepository = paymentRepository;
    }

    public KakaoReadyDto paymentUrl(UserName userName, List<Long> courseIds) throws URISyntaxException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        List<Course> courses = courseRepository.findAllById(courseIds);

        kakaoPayItemVO = new KakaoPayItemVO(account, courses, cart);

        String itemNames = courses.stream()
                .map(course -> course.title().value())
                .collect(Collectors.joining(", "));

        String itemCodes = courses.stream()
                .map(course -> course.id().toString())
                .collect(Collectors.joining(", "));

        String totalPrice = courses.stream()
                .map(course -> course.price().value())
                .reduce(Integer::sum)
                .orElse(0)
                .toString();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", accountId.value().toString());
        params.add("item_name", itemNames);
        params.add("item_code", itemCodes);
        params.add("quantity", "1");
        params.add("total_amount", totalPrice);
        params.add("vat_amount", "0");
        params.add("tax_free_amount", "0");
        params.add("approval_url", approvalUrl);
        params.add("fail_url", failUrl);
        params.add("cancel_url", cancelUrl);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            transaction.commit();

            return new KakaoReadyDto(kakaoPayReadyVO.getNext_redirect_pc_url());
        } catch (RestClientException | URISyntaxException exception ) {
            transaction.rollback();

            List<Payment> payments = Payment.listOf(courses, account, cart, PaymentStatus.FAILED);
            paymentRepository.saveAll(payments);

            exception.printStackTrace();
            throw exception;
        }
    }

    public KakaoPayItemVO approve(PaymentRequestDto paymentRequestDto, AccountId accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", accountId.value().toString());
        params.add("pg_token", paymentRequestDto.getPgToken());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        transaction.begin();
        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);

            transaction.commit();
            return kakaoPayItemVO;
        } catch (RuntimeException | URISyntaxException e) {
            transaction.rollback();

            List<Course> courses = kakaoPayItemVO.getCourses();
            Cart cart = kakaoPayItemVO.getCart();
            Account account = kakaoPayItemVO.getAccount();

            List<Payment> payments = Payment.listOf(courses, account, cart, PaymentStatus.FAILED);

            paymentRepository.saveAll(payments);

            e.printStackTrace();
            throw new KakaoApprovalFail(e);
        }
    }
}
