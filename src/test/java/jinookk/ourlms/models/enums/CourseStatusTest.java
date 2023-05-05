package jinookk.ourlms.models.enums;

import jinookk.ourlms.exceptions.InvalidStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseStatusTest {
    @Test
    void getValue() {
        CourseStatus courseStatus = CourseStatus.valueOf("APPROVED");

        assertThat(courseStatus).isEqualTo(CourseStatus.APPROVED);
    }


    @Test
    void getString() {
        assertThat("approved").isEqualTo(CourseStatus.APPROVED.toString());
    }


    @Test
    void filter() {
        CourseStatus status = CourseStatus.PROCESSING;

        assertThat(status.filter(CourseStatus.APPROVED.toString())).isFalse();
        assertThat(status.filter(CourseStatus.PROCESSING.toString())).isTrue();

        CourseStatus status2 = CourseStatus.SUBMITTED;

        assertThat(status2.filter(CourseStatus.APPROVED.toString())).isFalse();
        assertThat(status2.filter(CourseStatus.SUBMITTED.toString())).isTrue();

        assertThrows(InvalidStatus.class, () -> {
            status2.filter(null);
        });
    }
}