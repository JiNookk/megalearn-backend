package jinookk.ourlms.models.enums;

public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
