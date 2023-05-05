package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.enums.Level;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateCourseServiceTest extends Fixture {
    CreateCourseService courseService;
    CourseRepository courseRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseService = new CreateCourseService(courseRepository, accountRepository);

        Course course = Fixture.course("내 강의");
        Account account = Fixture.account("account");

        Page<Course> courses = new PageImpl<>(List.of(course));

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(courseRepository.findAll((Pageable) any())).willReturn(courses);
        given(courseRepository.save(any())).willReturn(course);
        given(courseRepository.findAllById(any())).willReturn(List.of(course));

        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void create() {
        courseService.create(new CourseRequestDto("title", Level.BEGINNER.getName()), new UserName("userName@email.com"));

        verify(courseRepository).save(any());
    }
}
