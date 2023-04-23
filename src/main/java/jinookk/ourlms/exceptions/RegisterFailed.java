package jinookk.ourlms.exceptions;

public class RegisterFailed extends RuntimeException {
    public RegisterFailed(String userName) {
        super("userName already exist " + userName);
    }
}
