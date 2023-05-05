package jinookk.ourlms.models.enums;

import jinookk.ourlms.exceptions.InvalidStatus;

public enum CourseStatus {
    PROCESSING("PROCESSING"),
    SUBMITTED("SUBMITTED"),
    APPROVED("APPROVED"),
    DELETED("DELETED");

    private final String status;

    CourseStatus(String status) {
        this.status = status;
    }

    public static CourseStatus value(String status) {
        String upperCased = status.toUpperCase();
        return CourseStatus.valueOf(upperCased);
    }

    public boolean filter(String type) {
        if (type == null) {
            throw new InvalidStatus(type);
        }

        if (type.equals(CourseStatus.PROCESSING.toString()) ||
                type.equals(CourseStatus.SUBMITTED.toString()) ||
                type.equals(CourseStatus.APPROVED.toString())) {
            return this.status.equals(type);
        }

        return true;
    }

    @Override
    public String toString() {
        return status;
    }
}
