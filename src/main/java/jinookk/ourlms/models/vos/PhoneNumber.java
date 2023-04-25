package jinookk.ourlms.models.vos;

import jinookk.ourlms.models.exceptions.InvalidPhoneNumberLength;

import java.util.List;

public class PhoneNumber {
    private String value;

    public PhoneNumber() {
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
