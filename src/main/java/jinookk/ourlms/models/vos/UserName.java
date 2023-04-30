package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidEmailFormat;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class UserName {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String email;

    protected UserName() {
        this.email = null;
    }

    public UserName(String email) {
        this.email = email;
    }

    public String value() {
        return email;
    }

    public void isValidFormat() {
        if (!Pattern.matches(EMAIL_REGEX, this.email)) {
            throw new InvalidEmailFormat();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(jinookk.ourlms.models.vos.UserName.class) &&
                ((jinookk.ourlms.models.vos.UserName) other).email.equals(this.email);
    }

    @Override
    public String toString() {
        return "UserName: " + email;
    }
}
