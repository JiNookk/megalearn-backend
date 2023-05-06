package jinookk.ourlms.applications.kakao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jinookk.ourlms.models.vos.kakao.KakaoPayApprovalVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayItemVO;
import jinookk.ourlms.models.vos.kakao.KakaoPayReadyVO;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class KakaoAuthService {

    @Value("${kakao.api-key}")
    private String apiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

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
}
