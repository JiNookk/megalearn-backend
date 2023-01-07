package jinookk.ourlms.services;

import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.exceptions.KakaoApprovalFail;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.kakao.KakaoPayApprovalVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayReadyVO;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KakaoService {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private KakaoPayItemVO kakaoPayItemVO;

    private final CourseRepository courseRepository;

    public KakaoService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public KakaoReadyDto paymentUrl(AccountId accountId, List<Long> courseIds) {
        List<Course> courses = courseRepository.findAllById(courseIds);

        kakaoPayItemVO = new KakaoPayItemVO(courses);

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
        headers.add("Authorization", "KakaoAK 9d07c1bc33de94c771fa9acdf1d56676");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
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
        params.add("approval_url", "http://localhost:8080/purchaseSuccess");
        params.add("fail_url", "http://localhost:8080/purchaseFail");
        params.add("cancel_url", "http://localhost:8080/purchaseCancel");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            return new KakaoReadyDto(kakaoPayReadyVO.getNext_redirect_pc_url());
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new KakaoReadyDto("/pay");
    }

    public KakaoPayItemVO approve(PaymentRequestDto paymentRequestDto, AccountId accountId) {
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 9d07c1bc33de94c771fa9acdf1d56676");
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

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);

            return kakaoPayItemVO;
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            throw new KakaoApprovalFail(e);
        }
    }
}


