package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateLectureServiceTest {
    UpdateLectureService updateLectureService;
    LectureRepository lectureRepository;

    @BeforeEach
    void setup() {
        lectureRepository = mock(LectureRepository.class);
        updateLectureService = new UpdateLectureService(lectureRepository);

        Lecture lecture = Lecture.fake("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAll())
                .willReturn(List.of(lecture));

        given(lectureRepository.findAllByCourseId(any()))
                .willReturn(List.of(lecture));
    }

    @Test
    void update() {
        long lectureId = 1L;

        LectureUpdateRequestDto lectureUpdateRequestDto =
                new LectureUpdateRequestDto("updated", "url", "note", new LectureTimeDto(new LectureTime(1, 24)), "path");

        LectureDto lectureDto = updateLectureService.update(lectureId, lectureUpdateRequestDto);

        assertThat(lectureDto.getTitle()).isEqualTo("updated");
    }
}
