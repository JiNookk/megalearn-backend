package jinookk.ourlms.exceptions;

public class RefreshTokenInvalid extends RuntimeException {
    public RefreshTokenInvalid(String refreshToken) {
        super("refreshToken value is invalid: " + refreshToken);
    }
}
