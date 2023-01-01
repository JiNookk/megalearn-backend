package jinookk.ourlms.models.vos.status;

import jinookk.ourlms.models.exceptions.InvalidStatus;

import java.util.Objects;

public class Status {
    public static final String CREATED = "created";
    public static final String UNWATCHED = "unwatched";
    public static final String PROCESSING = "processing";
    public static final String APPROVED = "approved";
    public static final String DELETED = "deleted";
    public static final String COMPLETED = "completed";
    public static final String SUBMITTED = "submitted";
    private static final String ALL = "all";

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

    public void complete() {
        this.value = Status.COMPLETED;
    }

    public void update(String value) {
        this.value = value;
    }

    public boolean filter(String type) {
        if (type == null) {
            throw new InvalidStatus(type);
        }
        if (type.equals(Status.PROCESSING)) {
            return processing();
        }

        if (type.equals(Status.SUBMITTED)) {
            return submit();
        }

        if (type.equals(Status.APPROVED)) {
            return approve();
        }

        return type.equals(Status.ALL);
    }

    public boolean processing() {
        return this.value.equals(Status.PROCESSING);
    }

    public boolean submit() {
        return this.value.equals(Status.SUBMITTED);
    }

    public boolean approve() {
        return this.value.equals(Status.APPROVED);
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
