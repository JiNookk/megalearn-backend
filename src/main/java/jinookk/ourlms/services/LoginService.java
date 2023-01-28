package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.InvalidPassword;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class LoginService {
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginService(AccountRepository accountRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResultDto kakaoLogin(Map<String, Object> userInfo) {
        Name name = new Name(String.valueOf(userInfo.get("nickname")));
        Name userName = new Name(String.valueOf(userInfo.get("email")));

        // 회원 없을 경우 -> 회원가입
        if (!accountRepository.existsByUserName(userName)) {
            Account account = new Account(name, userName);

            accountRepository.save(account);

            String accessToken = jwtUtil.encode(userName);

            return new LoginResultDto(accessToken, name, userName, account.phoneNumber());
        }

        // 회원 있을 경우 -> 로그인
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        String accessToken = jwtUtil.encode(userName);

        return new LoginResultDto(accessToken, account.name(), userName, account.phoneNumber());
    }

    public Account login(Name userName, String password) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(LoginFailed::new);

        if (!account.authenticate(password, passwordEncoder)) {
            throw new InvalidPassword();
        }

        return account;
    }
}
