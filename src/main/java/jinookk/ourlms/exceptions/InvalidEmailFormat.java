package jinookk.ourlms.exceptions;

public class InvalidEmailFormat extends RegisterFailed {
    public InvalidEmailFormat() {
        super("Email must contain @ and .!");
    }
}
