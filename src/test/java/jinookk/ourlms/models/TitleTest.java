package jinookk.ourlms.models;

import jinookk.ourlms.models.vos.Title;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TitleTest {
    @Test
    void equals() {
        Title title = new Title("title");

        assertThat(title.equals(new Title("title"))).isTrue();
        assertThat(title.equals(null)).isFalse();
        assertThat(title.equals("title")).isFalse();
    }
}
