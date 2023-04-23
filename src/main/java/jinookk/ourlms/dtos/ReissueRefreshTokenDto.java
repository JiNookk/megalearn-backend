package jinookk.ourlms.dtos;

public class ReissueRefreshTokenDto {
    private String userName;
    private String password;

    public ReissueRefreshTokenDto() {
    }

    public ReissueRefreshTokenDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
