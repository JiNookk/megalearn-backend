package jinookk.ourlms.exceptions;

public class RefreshTokenExpired extends RuntimeException {
    public RefreshTokenExpired(String refreshToken) {
        super("refreshToken is Expired: " + refreshToken);
    }
}
