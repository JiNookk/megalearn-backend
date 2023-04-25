package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.models.vos.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {
    RegisterRequestDto registerRequestDto;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        registerRequestDto =
                new RegisterRequestDto("UserName", "password", "name", "01085568965");

        passwordEncoder = new Argon2PasswordEncoder();
    }

    @Test
    void createdFromDto() {
        Account account = Account.of(registerRequestDto);

        assertThat(account.userName()).isEqualTo(new Name("UserName"));
    }

    @Test
    void authenticate() {
        Account account = Account.of(registerRequestDto);

        account.changePassword(registerRequestDto.getPassword(), passwordEncoder);

        assertThat(account.authenticate("password", passwordEncoder)).isTrue();
    }
}