package jinookk.ourlms.models.vos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTest {
    @Test
    void update() {
        Content content = new Content("value");

        content.update("updated");

        assertThat(new Content("updated")).isEqualTo(content);
    }
}
