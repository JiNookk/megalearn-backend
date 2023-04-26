package jinookk.ourlms.exceptions;

public class RegisterFailed extends RuntimeException {
    public RegisterFailed(String errorMessage) {
        super(errorMessage);
    }
}
