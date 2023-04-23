package jinookk.ourlms.exceptions;

public class LoginFailed extends RuntimeException {
    public LoginFailed(RuntimeException e) {
        super(e.getMessage());
    }
}
