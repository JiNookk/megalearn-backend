package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LectureRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteLectureServiceTest {
    DeleteLectureService deleteLectureService;
    LectureRepository lectureRepository;

    @BeforeEach
    void setup() {
        lectureRepository = mock(LectureRepository.class);
        deleteLectureService = new DeleteLectureService(lectureRepository);

        Lecture lecture = Fixture.lecture("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAll())
                .willReturn(List.of(lecture));

        given(lectureRepository.findAllByCourseId(any()))
                .willReturn(List.of(lecture));
    }

    @Test
    void delete() {
        long lectureId = 1L;

        LectureDto lectureDto = deleteLectureService.delete(lectureId);

        assertThat(lectureDto.getStatus()).isEqualTo(Status.DELETED);
    }
}
