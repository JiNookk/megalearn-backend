package jinookk.ourlms.services;

import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.InvalidPassword;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account authenticate(UserName userName, String password) {
        try {
            Account account = accountRepository.findByUserName(userName)
                    .orElseThrow(() -> new AccountNotFound(userName));

            if (!account.authenticate(password, passwordEncoder)) {
                throw new InvalidPassword();
            }

            return account;
        } catch (AccountNotFound | InvalidPassword e) {
            throw new LoginFailed(e);
        }
    }
}
