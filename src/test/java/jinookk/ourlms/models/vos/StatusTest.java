package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidStatus;
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

}
