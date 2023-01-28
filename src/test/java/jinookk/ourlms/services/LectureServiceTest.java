package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
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

class LectureServiceTest {
    LectureService lectureService;
    LectureRepository lectureRepository;
    CourseRepository courseRepository;
    PaymentRepository paymentRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        courseRepository = mock(CourseRepository.class);
        lectureRepository = mock(LectureRepository.class);
        lectureService = new LectureService(lectureRepository, courseRepository, paymentRepository, accountRepository);

        Lecture lecture = Lecture.fake("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAll())
                .willReturn(List.of(lecture));

        given(lectureRepository.findAllByCourseId(any()))
                .willReturn(List.of(lecture));

        Course course = Course.fake("course");
        given(courseRepository.findAllByAccountId(new AccountId(1L))).willReturn(List.of(course));

        Payment payment = Payment.fake(35000);
        given(paymentRepository.findAllByAccountId(any())).willReturn(List.of(payment));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void create() {
        Lecture lecture = Lecture.fake("title");

        given(lectureRepository.save(any())).willReturn(lecture);

        LectureDto sectionDto = lectureService.create(new LectureRequestDto(1L, 1L, "title", 1, 24));

        assertThat(sectionDto.getTitle()).isEqualTo("title");
    }

    @Test
    void detail() {
        LectureDto lectureDto = lectureService.detail(1L);

        assertThat(lectureDto).isNotNull();

        assertThat(lectureDto.getId()).isEqualTo(1L);

        verify(lectureRepository).findById(1L);
    }

    @Test
    void list() {
        LecturesDto lecturesDto = lectureService.list();

        assertThat(lecturesDto).isNotNull();

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void listByCourseId() {
        LecturesDto lecturesDto = lectureService.listByCourseId(new CourseId(1L));

        assertThat(lecturesDto).isNotNull();

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void listByInstructorId() {
        LecturesDto lecturesDto = lectureService.listByInstructorId(new Name("userName"));

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void myLectures() {
        LecturesDto lecturesDto = lectureService.myLectures(new Name("userName"));

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void update() {
        long lectureId = 1L;

        LectureUpdateRequestDto lectureUpdateRequestDto =
                new LectureUpdateRequestDto("updated", "url", "note", new LectureTimeDto(new LectureTime(1, 24)), "path");

        LectureDto lectureDto = lectureService.update(lectureId, lectureUpdateRequestDto);

        assertThat(lectureDto.getTitle()).isEqualTo("updated");
    }

    @Test
    void delete() {
        long lectureId = 1L;

        LectureDto lectureDto = lectureService.delete(lectureId);

        assertThat(lectureDto.getTitle()).isEqualTo(null);
    }
}
