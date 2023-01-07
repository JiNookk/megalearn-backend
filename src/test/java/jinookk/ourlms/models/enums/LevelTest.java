package jinookk.ourlms.models.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelTest {
    @Test
    void enums() {
        assertThat(Level.INTERMEDIATE.getName()).isEqualTo("초급");

        assertThat(Level.of("초급")).isEqualTo(Level.INTERMEDIATE);
    }
}
