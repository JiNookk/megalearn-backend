package jinookk.ourlms.models.vos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void delete() {
        Status status = new Status(Status.CREATED);

        assertThat(status.value()).isEqualTo("created");

        status.delete();

        assertThat(status.value()).isEqualTo("deleted");
    }
}