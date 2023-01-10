package jinookk.ourlms.models.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LikeTest {
    @Test
    void toggle() {
        Like like = Like.fake(false);

        assertThat(like.clicked()).isEqualTo(false);

        Like toggled = like.toggle();

        assertThat(toggled.clicked()).isEqualTo(true);
    }
}
