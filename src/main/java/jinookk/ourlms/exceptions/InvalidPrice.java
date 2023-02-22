package jinookk.ourlms.exceptions;

public class InvalidPrice extends RuntimeException {
    public InvalidPrice(Integer value) {
        super("value should be at least 10000 or 0: " + value);
    }
}
