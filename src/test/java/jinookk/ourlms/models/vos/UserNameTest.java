package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidEmailFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNameTest {
    @Test
    void validateWithValidFormat() {
        UserName userName = new UserName("ojw0828@naver.com");
        assertDoesNotThrow(userName::isValidFormat);
    }

    @Test
    void validateWithoutDot() {
        UserName userName = new UserName("ojw0828@com");
        assertThrows(InvalidEmailFormat.class, userName::isValidFormat);
    }

    @Test
    void validateWithoutSymbol() {
        UserName userName = new UserName("ojw0828.com");
        assertThrows(InvalidEmailFormat.class, userName::isValidFormat);
    }

    @Test
    void validateWithoutDotAndSymbol() {
        UserName userName = new UserName("ojw0828");
        assertThrows(InvalidEmailFormat.class, userName::isValidFormat);
    }

    @Test
    void validateWithBlankUsername() {
        UserName userName = new UserName("");
        assertThrows(InvalidEmailFormat.class, userName::isValidFormat);
    }
}
