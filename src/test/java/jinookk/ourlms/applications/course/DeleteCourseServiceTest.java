package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
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

class DeleteCourseServiceTest {
    DeleteCourseService courseService;
    CourseRepository courseRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseService = new DeleteCourseService(courseRepository, accountRepository);

        Course course = Course.fake("내 강의");
        Account account = Account.fake("account");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        Page<Course> courses = new PageImpl<>(List.of(course));

        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));

        given(courseRepository.findAll((Pageable) any())).willReturn(courses);
        given(courseRepository.save(any())).willReturn(course);
        given(courseRepository.findAllById(any())).willReturn(List.of(course));
    }

    @Test
    void delete() {
        Course course = Course.fake("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.delete(1L, new UserName("tester@email.com"));

        assertThat(courseDto.getStatus()).isEqualTo(Status.DELETED);
    }

    @Test
    void deleteSkill() {
        Course course = Course.fake("updated");

        CourseUpdateRequestDto courseUpdateRequestDto = new CourseUpdateRequestDto(
                "updated", "category", "description", "thumbnailPath", "", "초급", List.of("스킬"), 0);

        course.update(courseUpdateRequestDto, new AccountId(1L));

        assertThat(course.skillSets()).hasSize(1);

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.deleteSkill(new CourseId(1L), new HashTag("스킬"), new UserName("tester@email.com"));

        assertThat(courseDto.getSkillSets()).hasSize(0);
    }
}
