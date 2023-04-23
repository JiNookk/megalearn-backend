package jinookk.ourlms.exceptions;

public class InvalidStatus extends RuntimeException{
    public InvalidStatus(String type) {
        super("type is not valid: " + type);
    }
}
