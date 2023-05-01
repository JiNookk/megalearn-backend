package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateLectureServiceTest {
    CreateLectureService createLectureService;
    LectureRepository lectureRepository;

    @BeforeEach
    void setup() {
        lectureRepository = mock(LectureRepository.class);
        createLectureService = new CreateLectureService(lectureRepository);

        Lecture lecture = Lecture.fake("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAll())
                .willReturn(List.of(lecture));

        given(lectureRepository.findAllByCourseId(any()))
                .willReturn(List.of(lecture));
    }

    @Test
    void create() {
        Lecture lecture = Lecture.fake("title");

        given(lectureRepository.save(any())).willReturn(lecture);

        LectureDto sectionDto = createLectureService.create(new LectureRequestDto(1L, 1L, "title", 1, 24));

        assertThat(sectionDto.getTitle()).isEqualTo("title");
    }
}
