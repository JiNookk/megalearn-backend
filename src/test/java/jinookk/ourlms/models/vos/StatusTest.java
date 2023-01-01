package jinookk.ourlms.models.vos;

import jinookk.ourlms.models.exceptions.InvalidStatus;
import jinookk.ourlms.models.vos.status.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTest {

    @Test
    void delete() {
        Status status = new Status(Status.CREATED);

        assertThat(status.value()).isEqualTo("created");

        status.delete();

        assertThat(status.value()).isEqualTo("deleted");
    }

    @Test
    void processing() {
        Status status = new Status(Status.PROCESSING);

        assertThat(status.processing()).isTrue();
    }

    @Test
    void submit() {
        Status status = new Status(Status.SUBMITTED);

        assertThat(status.submit()).isTrue();
    }

    @Test
    void approved() {
        Status status = new Status(Status.APPROVED);

        assertThat(status.approve()).isTrue();
    }

    @Test
    void filter() {
        Status status = new Status(Status.PROCESSING);

        assertThat(status.filter(Status.APPROVED)).isFalse();
        assertThat(status.filter(Status.PROCESSING)).isTrue();

        Status status2 = new Status(Status.SUBMITTED);

        assertThat(status2.filter(Status.APPROVED)).isFalse();
        assertThat(status2.filter(Status.SUBMITTED)).isTrue();

        assertThrows(InvalidStatus.class, () -> {
            status2.filter(null);
        });
    }
}
