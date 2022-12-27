package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Like {
    private Long accountId;

    public Like() {
    }

    public Like(Long accountId) {
        this.accountId = accountId;
    }

    public Long accountId() {
        return accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Like.class) &&
                ((Like) other).accountId.equals(this.accountId);
    }

    @Override
    public String toString() {
        return "Like accountId: " + accountId;
    }
}
