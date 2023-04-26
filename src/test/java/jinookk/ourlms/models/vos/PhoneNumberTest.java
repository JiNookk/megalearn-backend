package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidPhoneNumberLength;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {
    @Test
    void validateLengthWithCorrectLength() {
        PhoneNumber phoneNumber = new PhoneNumber();

        assertDoesNotThrow(() -> {
            phoneNumber.isAppropriateLength("01012345678");
        });
    }

    @Test
    void validateLengthWithIncorrectLength() {
        PhoneNumber phoneNumber = new PhoneNumber();

        assertThrows(InvalidPhoneNumberLength.class, () -> {
            phoneNumber.isAppropriateLength("010123456");
        });
        assertThrows(InvalidPhoneNumberLength.class, () -> {
            phoneNumber.isAppropriateLength("0101234567890");
        });
    }

    @Test
    void setNumberFormat() {
        PhoneNumber phoneNumber = new PhoneNumber();

        assertThat(phoneNumber.setNumberFormat("01085568965")).isEqualTo("010-8556-8965");
    }
}