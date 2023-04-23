package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LoginRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;
    private String tokenValue;

    public RefreshToken() {
    }

    public RefreshToken(String userName, String value) {
        this.userName = userName;
        this.tokenValue = value;
    }

    public static RefreshToken of(LoginRequestDto loginRequestDto, String tokenValue) {
        String userName = loginRequestDto.getEmail();

        return new RefreshToken(userName, tokenValue);
    }

    public static RefreshToken fake(String expired) {
        return new RefreshToken("tester", expired);
    }


    public String tokenValue() {
        return tokenValue;
    }

    public String userName() {
        return userName;
    }

    public void updateTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Long id() {
        return id;
    }
}
