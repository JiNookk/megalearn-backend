package jinookk.ourlms.models.vos;

import java.util.Objects;

public class AccountId {
    private Long value;

    public AccountId() {
    }

    public AccountId(Long value) {
        this.value = value;
    }


    public Long value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(AccountId.class) &&
                ((AccountId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "AccountId value: " + value;
    }
}
