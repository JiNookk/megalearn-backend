package jinookk.ourlms.models.vos;

import jinookk.ourlms.dtos.LectureTimeDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LectureTimeTest {
    @Test
    void convertToDto() {
        LectureTime lectureTime = new LectureTime(1, 24);

        LectureTimeDto lectureTimeDto = lectureTime.toDto();

        assertThat(lectureTimeDto.getMinute()).isEqualTo(1);
        assertThat(lectureTimeDto.getSecond()).isEqualTo(24);
    }

    @Test
    void update() {
        LectureTime lectureTime = new LectureTime(1, 24);

        LectureTime updated = lectureTime.update(new LectureTime(5, 30).toDto());

        assertThat(updated.minute()).isEqualTo(5);
        assertThat(updated.second()).isEqualTo(30);
    }
}
