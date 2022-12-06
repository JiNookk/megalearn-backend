package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LectureServiceTest {
    LectureService lectureService;
    LectureRepository lectureRepository;

    @BeforeEach
    void setup() {
        lectureRepository = mock(LectureRepository.class);
        lectureService = new LectureService(lectureRepository);

        Lecture lecture = Lecture.fake("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAllByCourseId(1L))
                .willReturn(List.of(lecture));
    }

    @Test
    void detail() {
        LectureDto lectureDto = lectureService.detail(1L);

        assertThat(lectureDto).isNotNull();

        assertThat(lectureDto.getId()).isEqualTo(31L);

        verify(lectureRepository).findById(1L);
    }

    @Test
    void list() {
        LecturesDto lecturesDto = lectureService.list(1L);

        assertThat(lecturesDto).isNotNull();

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }
}
