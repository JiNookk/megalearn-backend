package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CourseServiceTest {
    CourseService courseService;
    CourseRepository courseRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository, accountRepository);

        Course course = Course.fake("내 강의");
        Account account = Account.fake("account");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(courseRepository.findAll()).willReturn(List.of(course));
        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
        given(courseRepository.save(any())).willReturn(course);
    }

    @Test
    void detail() {
        CourseDto courseDto = courseService.detail(1L);

        assertThat(courseDto).isNotNull();

        verify(courseRepository).findById(1L);
    }

    @Test
    void list() {
        CoursesDto coursesDto = courseService.list();

        assertThat(coursesDto.getCourses()).hasSize(1);
    }

    @Test
    void create() {
        courseService.create(new CourseRequestDto("title"), new AccountId(1L));

        verify(accountRepository).findById(1L);
        verify(courseRepository).save(any());
    }

    @Test
    void update() {
        Course course = Course.fake("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.update(
                1L, new CourseUpdateRequestDto("updated", "category", "description", "thumbnailPath", "status", 0));

        assertThat(courseDto.getTitle()).isEqualTo("updated");
    }

    @Test
    void delete() {
        Course course = Course.fake("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.delete(1L);

        assertThat(courseDto.getTitle()).isEqualTo(null);
    }
}
