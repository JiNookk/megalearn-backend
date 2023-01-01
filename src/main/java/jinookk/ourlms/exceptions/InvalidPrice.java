package jinookk.ourlms.exceptions;

public class InvalidPrice extends RuntimeException {
    public InvalidPrice(Integer value) {
        super("value is invalid: " + value);
    }
}
