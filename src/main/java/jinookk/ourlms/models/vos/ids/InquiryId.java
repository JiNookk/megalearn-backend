package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class InquiryId {
    private Long value;

    public InquiryId() {
    }

    public InquiryId(Long value) {
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
                other.getClass().equals(InquiryId.class) &&
                ((InquiryId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "InquiryId value: " + value;
    }
}
