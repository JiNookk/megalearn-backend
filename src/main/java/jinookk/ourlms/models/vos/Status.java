package jinookk.ourlms.models.vos;

import java.util.Objects;

public class Status {
    public static final String CREATED = "created";
    private static final String DELETED = "deleted";

    private String value;

    public Status() {
    }

    public Status(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public void delete() {
        this.value = Status.DELETED;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Status.class) &&
                ((Status) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Status value: " + value;
    }
}
