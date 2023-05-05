package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.UserName;
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

class GetLectureServiceTest extends Fixture {
    GetLectureService getLectureService;
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
        getLectureService = new GetLectureService(lectureRepository, courseRepository, paymentRepository, accountRepository);

        Lecture lecture = Fixture.lecture("테스트 1강");

        given(lectureRepository.findById(1L))
                .willReturn(Optional.of(lecture));

        given(lectureRepository.findAll())
                .willReturn(List.of(lecture));

        given(lectureRepository.findAllByCourseId(any()))
                .willReturn(List.of(lecture));

        Course course = Fixture.course("course");
        given(courseRepository.findAllByAccountId(new AccountId(1L))).willReturn(List.of(course));

        Payment payment = Fixture.payment(35000);
        given(paymentRepository.findAllByAccountId(any())).willReturn(List.of(payment));

        Account account = Fixture.account("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void detail() {
        LectureDto lectureDto = getLectureService.detail(1L);

        assertThat(lectureDto).isNotNull();

        assertThat(lectureDto.getId()).isEqualTo(1L);

        verify(lectureRepository).findById(1L);
    }

    @Test
    void list() {
        LecturesDto lecturesDto = getLectureService.list();

        assertThat(lecturesDto).isNotNull();

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void listByCourseId() {
        LecturesDto lecturesDto = getLectureService.listByCourseId(new CourseId(1L));

        assertThat(lecturesDto).isNotNull();

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void listByInstructorId() {
        LecturesDto lecturesDto = getLectureService.listByInstructorId(new UserName("userName@email.com"));

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }

    @Test
    void myLectures() {
        LecturesDto lecturesDto = getLectureService.myLectures(new UserName("userName@email.com"));

        assertThat(lecturesDto.getLectures()).hasSize(1);
    }
}
