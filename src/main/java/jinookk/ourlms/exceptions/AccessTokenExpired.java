package jinookk.ourlms.exceptions;

public class AccessTokenExpired extends RuntimeException {
    public AccessTokenExpired() {
        super("accessToken Expired");
    }
}
