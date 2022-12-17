package jinookk.ourlms.models.vos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUrlTest {
    @Test
    void equals() {
        VideoUrl videoUrl = new VideoUrl("videoUrl");

        assertThat(videoUrl.equals(new VideoUrl("videoUrl"))).isTrue();
        assertThat(videoUrl.equals(null)).isFalse();
        assertThat(videoUrl.equals("videoUrl")).isFalse();
    }
}
