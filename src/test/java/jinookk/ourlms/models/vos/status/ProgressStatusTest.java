package jinookk.ourlms.models.vos.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgressStatusTest {
    ProgressStatus progressStatus;

    @BeforeEach
    void setup() {
        progressStatus = new ProgressStatus();
    }

    @Test
    void complete() {
        assertThat(progressStatus.value()).isEqualTo(ProgressStatus.PROCESSING);
        assertThat(progressStatus.completedAt()).isNull();

        ProgressStatus completed = progressStatus.complete();

        assertThat(completed.value()).isEqualTo(ProgressStatus.COMPLETED);
        assertThat(completed.completedAt()).isNotNull();
    }
}