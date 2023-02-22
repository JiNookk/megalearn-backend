package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.models.exceptions.InvalidArgument;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProgressTest {
    @Test
    void complete() {
        Progress progress = Progress.fake("test");

        assertThat(progress.status().value()).isEqualTo("processing");

        progress.complete();

        assertThat(progress.status().value()).isEqualTo("completed");
        assertThat(progress.id()).isEqualTo(31L);
        assertThat(progress.title()).isEqualTo(new Title("test"));
    }

    @Test
    void updateTime() {
        Progress progress = Progress.fake("test");

        Progress updated = progress.updateTime(new LectureTimeDto(new LectureTime(5, 30)));

        assertThat(updated.lectureTime()).isEqualTo(new LectureTime(5, 30));
    }

    @Test
    void createdFromLecture() {
        Lecture lecture = Lecture.fake(new LectureId(1L));

        AccountId accountId = new AccountId(1L);

        Progress progress = Progress.of(lecture, accountId);

        assertThat(progress).isNotNull();
    }

    @Test
    void createdFromNull() {
        assertThrows(InvalidArgument.class, () -> {
            Progress.of(null, new AccountId(1L));
        });

        assertThrows(InvalidArgument.class, () -> {
            Progress.of(null, null);
        });

        assertThrows(InvalidArgument.class, () -> {
            Progress.of(Lecture.fake(new LectureId(1L)), null);
        });
    }

    @Test
    void collectionWithValidLectures() {
        List<Lecture> lectures = List.of(
                Lecture.fake(new LectureId(1L)), Lecture.fake(new LectureId(2L)), Lecture.fake(new LectureId(3L)));

        AccountId accountId = new AccountId(1L);

        List<Progress> progresses = Progress.listOf(lectures, accountId);

        assertThat(progresses).hasSize(3);
    }

    @Test
    void collectionWithInvalidValue() {
        AccountId accountId = new AccountId(1L);

        assertThrows(InvalidArgument.class, () -> {
            Progress.listOf(null, accountId);
        });

        assertThrows(InvalidArgument.class, () -> {
            Progress.listOf(null, null);
        });

        assertThrows(InvalidArgument.class, () -> {
            Progress.listOf(List.of(), null);
        });
    }
}
