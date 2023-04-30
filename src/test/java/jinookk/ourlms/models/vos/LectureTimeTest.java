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
}
