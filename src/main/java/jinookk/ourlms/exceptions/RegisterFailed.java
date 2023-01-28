package jinookk.ourlms.exceptions;

public class RegisterFailed extends RuntimeException {
    public RegisterFailed(String userName) {
        super("userName alreadyExist " + userName);
    }
}
