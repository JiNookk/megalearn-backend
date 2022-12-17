package jinookk.ourlms.models;

import jinookk.ourlms.models.vos.ImagePath;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImagePathTest {
    @Test
    void equals() {
        ImagePath imagePath = new ImagePath("imagePath");

        assertThat(imagePath.equals(new ImagePath("imagePath"))).isTrue();
        assertThat(imagePath.equals(null)).isFalse();
        assertThat(imagePath.equals("imagePath")).isFalse();
    }
}