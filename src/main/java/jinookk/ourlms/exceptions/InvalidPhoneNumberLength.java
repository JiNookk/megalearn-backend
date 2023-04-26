package jinookk.ourlms.exceptions;

import jinookk.ourlms.exceptions.RegisterFailed;

public class InvalidPhoneNumberLength extends RegisterFailed {
    public InvalidPhoneNumberLength() {
        super("phone number length should be 11!");
    }
}
