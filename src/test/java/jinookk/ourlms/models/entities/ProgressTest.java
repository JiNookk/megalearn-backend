package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.models.vos.LectureTime;
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

    @Test
    void updateTime() {
        Progress progress = Progress.fake("test");

        Progress updated = progress.updateTime(new LectureTimeDto(new LectureTime(5, 30)));

        assertThat(updated.lectureTime()).isEqualTo(new LectureTime(5, 30));
    }
}
