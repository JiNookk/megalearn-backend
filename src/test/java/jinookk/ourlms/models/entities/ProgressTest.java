package jinookk.ourlms.models.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgressTest {
    @Test
    void complete() {
        Progress progress = Progress.fake("test");

        assertThat(progress.status().value()).isEqualTo("unwatched");

        progress.complete();

        assertThat(progress.status().value()).isEqualTo("completed");
    }
}
