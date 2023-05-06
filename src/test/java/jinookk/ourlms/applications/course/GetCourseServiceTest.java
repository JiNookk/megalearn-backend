package jinookk.ourlms.applications.course;

import jinookk.ourlms.daos.CourseDao;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.GetCoursesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
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
import static org.mockito.Mockito.verify;

class GetCourseServiceTest extends Fixture {
    GetCourseService getCourseService;
    CourseRepository courseRepository;
    AccountRepository accountRepository;
    LikeRepository likeRepository;
    CourseDao courseDao;

    @BeforeEach
    void setup() {
        likeRepository = mock(LikeRepository.class);
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseDao = mock(CourseDao.class);
        getCourseService = new GetCourseService(courseRepository, accountRepository, likeRepository, courseDao);

        Course course = Fixture.course("내 강의");
        Account account = Fixture.account("account");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        Page<Course> courses = new PageImpl<>(List.of(course));

        given(courseRepository.findAll((Pageable) any())).willReturn(courses);

        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
        given(courseRepository.save(any())).willReturn(course);

        Like like = Fixture.like(true);
        given(likeRepository.findAllByAccountId(any())).willReturn(List.of(like));

        given(courseRepository.findAllById(any())).willReturn(List.of(course));

        given(courseDao.findCoursesByFilter(any(), any())).willReturn(new PageImpl<>(List.of(course)));
        given(courseDao.findCoursesForAdmin(any())).willReturn(new PageImpl<>(List.of(course)));
    }

    @Test
    void detail() {
        CourseDto courseDto = getCourseService.detail(new UserName("userName@email.com"), new CourseId(1L));

        assertThat(courseDto).isNotNull();
        assertThat(courseDto.getPurchased()).isTrue();

        verify(courseRepository).findById(1L);
    }

    // TODO: EntityManager를 모킹하는 방법 찾기
    @Test
    void list() {
        CoursesDto coursesDto = getCourseService.list(1, new CourseFilterDto("입문", "cost", "skill", "content"));

        assertThat(coursesDto.getCourses()).hasSize(1);
    }

    @Test
    void listForAdmin() {
        CoursesDto coursesDto = getCourseService.listForAdmin(1);

        assertThat(coursesDto.getCourses()).hasSize(1);
    }

    @Test
    void wishList() {
        GetCoursesDto coursesDto = getCourseService.wishList(new UserName("userName@email.com"));

        assertThat(coursesDto.courses()).hasSize(1);
    }
}
