package jinookk.ourlms.models.vos.status;

public class InquiryStatus {
    private String replied;
    private String solved;
    private String value;

    public InquiryStatus() {
    }

    public InquiryStatus(String value) {
        this.value = value;
        this.replied = Status.PROCESSING;
        this.solved = Status.PROCESSING;
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
        this.value = Status.DELETED;
    }

    public boolean filter(String status) {
        if (status == null) {
            return true;
        }

        if (status.equals("unreplied")) {
            return replied.equals(Status.PROCESSING);
        }

        if (status.equals("replied")) {
            return replied.equals(Status.COMPLETED);
        }

        if (status.equals("unsolved")) {
            return solved.equals(Status.PROCESSING);
        }

        if (status.equals("solved")) {
            return solved.equals(Status.COMPLETED);
        }

        return true;
    }

    public void reply() {
        this.replied = Status.COMPLETED;
    }

    public void solve() {
        this.solved = Status.COMPLETED;
    }
}
