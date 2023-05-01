package jinookk.ourlms.applications.progress;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.ProgressId;
import jinookk.ourlms.repositories.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateProgressServiceTest {
    ProgressRepository progressRepository;
    UpdateProgressService updateProgressService;

    @BeforeEach
    void setup() {
        progressRepository = mock(ProgressRepository.class);
        updateProgressService = new UpdateProgressService(progressRepository);

        Progress progress = Progress.fake("1강");

        given(progressRepository.findById(1L))
                .willReturn(Optional.of(progress));

        given(progressRepository.findByLectureIdAndAccountId(new LectureId(1L), new AccountId(1L)))
                .willReturn(Optional.of(progress));

        given(progressRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(progress));

        given(progressRepository.findAllByAccountId(any(), any()))
                .willReturn(List.of(progress));

        given(progressRepository.findAll((Specification<Progress>) any(), (Sort) any()))
                .willReturn(List.of(progress));
    }

    @Test
    void complete() {
        ProgressDto progressDto = updateProgressService.complete(new ProgressId(1L));

        assertThat(progressDto.getStatus()).isEqualTo("completed");
    }

    @Test
    void updateTime() {
        LectureTime lectureTime = new LectureTime(5, 30);

        ProgressDto progressDto = updateProgressService.updateTime(new ProgressId(1L), new LectureTimeDto(lectureTime));

        assertThat(progressDto.getLectureTime().getMinute()).isEqualTo(5);
        assertThat(progressDto.getLectureTime().getSecond()).isEqualTo(30);
    }
}
