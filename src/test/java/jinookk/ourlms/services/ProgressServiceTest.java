package jinookk.ourlms.services;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProgressServiceTest {
    ProgressRepository progressRepository;
    ProgressService progressService;

    @BeforeEach
    void setup() {
        progressRepository = mock(ProgressRepository.class);
        progressService = new ProgressService(progressRepository);

        Progress progress = Progress.fake("1강");

        given(progressRepository.findById(1L))
                .willReturn(Optional.of(progress));

        given(progressRepository.findByLectureId(new LectureId(1L)))
                .willReturn(Optional.of(progress));

        given(progressRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(progress));
    }

    @Test
    void detail() {
        ProgressDto progressDto = progressService.detail(new LectureId(1L));

        assertThat(progressDto).isNotNull();
    }

    @Test
    void list() {
        ProgressesDto progressesDto = progressService.list(new CourseId(1L));

        assertThat(progressesDto.getProgresses()).hasSize(1);
    }

    @Test
    void complete() {
        ProgressDto progressDto = progressService.complete(1L);

        assertThat(progressDto.getStatus()).isEqualTo("completed");
    }
}
