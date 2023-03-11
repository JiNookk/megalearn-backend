package jinookk.ourlms.exceptions;

public class AuthenticationError extends RuntimeException {
    public AuthenticationError(String accessToken) {
        super("Cannot Authenticate! accessToken: " + accessToken);
    }
}
