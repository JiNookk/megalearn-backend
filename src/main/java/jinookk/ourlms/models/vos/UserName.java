package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidEmailFormat;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class UserName {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String value;

    public UserName() {
        this.value = null;
    }

    public UserName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public void isValidFormat() {
        if (!Pattern.matches(USERNAME_REGEX, this.value)) {
            throw new InvalidEmailFormat();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(jinookk.ourlms.models.vos.UserName.class) &&
                ((jinookk.ourlms.models.vos.UserName) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "UserName value: " + value;
    }
}
