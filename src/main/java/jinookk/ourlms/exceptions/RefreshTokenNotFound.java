package jinookk.ourlms.exceptions;

public class RefreshTokenNotFound extends RuntimeException {
    public RefreshTokenNotFound(String expiredToken) {
        super("RefreshToken is not found by value: " + expiredToken);
    }
}
