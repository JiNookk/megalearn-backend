package jinookk.ourlms.applications.kakao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jinookk.ourlms.dtos.KakaoReadyDto;
import jinookk.ourlms.dtos.PaymentRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.KakaoApprovalFail;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.kakao.KakaoPayApprovalVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayReadyVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class KakaoService {
    private static final String HOST = "https://kapi.kakao.com";

    @Value("${kakao.api-key}")
    private String apiKey;

    @Value("${kakao.admin-key}")
    private String adminKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.approval.url}")
    private String approvalUrl;

    @Value("${kakao.fail.url}")
    private String failUrl;

    @Value("${kakao.cancel.url}")
    private String cancelUrl;

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private KakaoPayItemVO kakaoPayItemVO;

    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;


    public KakaoService(CourseRepository courseRepository, AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public String getAccessToken(String code) throws IOException {
        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + apiKey);
            sb.append("&redirect_uri=" + redirectUri);
            System.out.println(redirectUri);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonElement element = JsonParser.parseString(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();

            throw e;
        }

        return accessToken;
    }

    public Map<String, Object> getUser(String accessToken) {
        Map<String, Object> user = new HashMap<>();

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonElement element = JsonParser.parseString(result);

            JsonObject properties =
                    element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount =
                    element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            user.put("nickname", nickname);
            user.put("email", email);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public KakaoReadyDto paymentUrl(UserName userName, List<Long> courseIds) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

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

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);

            return kakaoPayItemVO;
//        } catch (RuntimeException | RestClientException |  URISyntaxException e) {
        } catch (RuntimeException | URISyntaxException e) {
            e.printStackTrace();
            throw new KakaoApprovalFail(e);
        }
    }
}
