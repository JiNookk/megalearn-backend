package jinookk.ourlms.models.vos.status;

import java.util.Objects;

public class InquiryStatus {
    public static final String CREATED = "created";
    public static final String COMPLETED = "completed";
    public static final String DELETED = "deleted";
    public static final String PROCESSING = "processing";

    private String replied;
    private String solved;
    private String value;

    public InquiryStatus() {
        this.value = CREATED;
        this.replied = PROCESSING;
        this.solved = PROCESSING;
    }

    public String replied() {
        return replied;
    }

    public String solved() {
        return solved;
    }

    public String value() {
        return value;
    }

    public void delete() {
        this.value = DELETED;
    }

    public boolean filter(String status) {
        if (status == null) {
            return true;
        }

        if (status.equals("unreplied")) {
            return replied.equals(PROCESSING);
        }

        if (status.equals("replied")) {
            return replied.equals(COMPLETED);
        }

        if (status.equals("unsolved")) {
            return solved.equals(PROCESSING);
        }

        if (status.equals("solved")) {
            return solved.equals(COMPLETED);
        }

        return true;
    }

    public InquiryStatus reply() {
        this.replied = COMPLETED;

        return this;
    }

    public InquiryStatus toggleSolved() {
        this.solved = Objects.equals(this.solved(), COMPLETED)
                ? PROCESSING
                : COMPLETED;

        return this;
    }
}
