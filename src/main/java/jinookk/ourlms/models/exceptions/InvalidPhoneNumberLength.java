package jinookk.ourlms.models.exceptions;

public class InvalidPhoneNumberLength extends RuntimeException {
    public InvalidPhoneNumberLength() {
        super("phone number length should be 11!");
    }
}
