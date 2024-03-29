package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidPhoneNumberLength;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
public class PhoneNumber {
    private final String value;

    protected PhoneNumber() {
        this.value = null;
    }

    public PhoneNumber(String phoneNumber) {
        isAppropriateLength(phoneNumber);

        this.value = phoneNumber;
    }

    public void isAppropriateLength(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            throw new InvalidPhoneNumberLength();
        }
    }

    public String value() {
        return value;
    }

    public String setNumberFormat(String phoneNumber) {
        String countryCode = phoneNumber.substring(0, 3);
        String middleNumber = phoneNumber.substring(3, 7);
        String lastNumber = phoneNumber.substring(7);

        return String.join("-", List.of(countryCode, middleNumber, lastNumber));
    }
}
