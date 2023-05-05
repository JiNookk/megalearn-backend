package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateCourseServiceTest extends Fixture {
    UpdateCourseService updateCourseService;
    CourseRepository courseRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        updateCourseService = new UpdateCourseService(courseRepository, accountRepository);

        Course course = Fixture.course("내 강의");
        Account account = Fixture.account("account");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        Page<Course> courses = new PageImpl<>(List.of(course));

        given(courseRepository.findAll((Pageable) any())).willReturn(courses);

        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
        given(courseRepository.save(any())).willReturn(course);
        given(courseRepository.findAllById(any())).willReturn(List.of(course));
    }

    @Test
    void update() {
        Course course = Fixture.course("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = updateCourseService.update(
                1L, new CourseUpdateRequestDto("updated", "category", "description", "thumbnailPath", "processing",
                        "초급", List.of(), 0), new UserName("tester@email.com"));

        assertThat(courseDto.getTitle()).isEqualTo("updated");
    }

    @Test
    void updateStatus() {
        Course course = Fixture.course("updated");

        given(courseRepository.findByIdForUpdate(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = updateCourseService.updateStatus(1L, new StatusUpdateDto("approved"));

        assertThat(courseDto.getStatus()).isEqualTo(CourseStatus.APPROVED.toString());
    }
}
