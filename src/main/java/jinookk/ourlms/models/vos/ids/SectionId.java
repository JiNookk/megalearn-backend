package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class SectionId {
    private Long value;

    public SectionId() {
    }

    public SectionId(Long value) {
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
                other.getClass().equals(SectionId.class) &&
                ((SectionId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "SectionId value: " + value;
    }
}
