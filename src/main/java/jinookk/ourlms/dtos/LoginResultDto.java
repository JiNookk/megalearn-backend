package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;

public class LoginResultDto {
    private final String accessToken;
    private final String name;
    private final String userName;
    private final String phoneNumber;

    public LoginResultDto(String accessToken, Name name, UserName userName, String phoneNumber) {
        this.accessToken = accessToken;
        this.name = name.value();
        this.userName = userName.value();
        this.phoneNumber = phoneNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
