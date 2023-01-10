package jinookk.ourlms.models.vos.status;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProgressStatus {
    public static final String PROCESSING = "processing";
    public static final String COMPLETED = "completed";
    public static final String DELETED = "deleted";

    private String value = PROCESSING;

    private LocalDateTime completedAt;

    public ProgressStatus() {
    }

    public String value() {
        return value;
    }

    public LocalDateTime completedAt() {
        return completedAt;
    }

    public void delete() {
        this.value = ProgressStatus.DELETED;
    }

    public ProgressStatus complete() {
        this.value = ProgressStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();

        return this;
    }

    public void update(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(ProgressStatus.class) &&
                ((ProgressStatus) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "ProgressStatus value: " + value;
    }
}
