package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class AccountId {
    private final Long value;

    protected AccountId() {
        this.value = null;
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
                ((AccountId) other).value != null &&
                ((AccountId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "AccountId value: " + value;
    }
}
