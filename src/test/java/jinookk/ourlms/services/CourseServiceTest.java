package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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
    PaymentRepository paymentRepository;
    LikeRepository likeRepository;

    @BeforeEach
    void setup() {
        likeRepository = mock(LikeRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        accountRepository = mock(AccountRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository, accountRepository, paymentRepository, likeRepository);

        Course course = Course.fake("내 강의");
        Account account = Account.fake("account");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        Page<Course> courses = new PageImpl<>(List.of(course));

        given(courseRepository.findAll((Specification<Course>) any(), (Pageable) any())).willReturn(courses);

        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
        given(courseRepository.save(any())).willReturn(course);

        Payment payment = Payment.fake(35_000);

        given(paymentRepository.findByAccountIdAndCourseId(new AccountId(1L), new CourseId(1L)))
                .willReturn(Optional.of(payment));

        Like like = Like.fake(true);
        given(likeRepository.findAllByAccountId(any())).willReturn(List.of(like));

        given(courseRepository.findAllById(any())).willReturn(List.of(course));
    }

    @Test
    void detail() {
        CourseDto courseDto = courseService.detail(new AccountId(1L), new CourseId(1L));

        assertThat(courseDto).isNotNull();
        assertThat(courseDto.getIsPurchased()).isTrue();

        verify(courseRepository).findById(1L);
    }

    @Test
    void list() {
        CoursesDto coursesDto = courseService.list(1, new CourseFilterDto("입문", "cost", "skill", "content"));

        assertThat(coursesDto.getCourses()).hasSize(1);
    }


    // 좋아요 목록 => 일단 유저 아이디로 좋아요 목록을 불러온다.
    // 좋아요 목록에 있는 courseId들을 list로 불러온다.
    // courseId 컬렉션으로 강의들을 찾는다.

    @Test
    void wishList() {
        CoursesDto coursesDto = courseService.wishList(new AccountId(1L));

        assertThat(coursesDto.getCourses()).hasSize(1);
    }

    @Test
    void create() {
        courseService.create(new CourseRequestDto("title", Level.BEGINNER.getName()), new AccountId(1L));

        verify(accountRepository).findById(1L);
        verify(courseRepository).save(any());
    }

    @Test
    void update() {
        Course course = Course.fake("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.update(
                1L, new CourseUpdateRequestDto("updated", "category", "description", "thumbnailPath", "status",
                        "초급", "skill", 0));

        assertThat(courseDto.getTitle()).isEqualTo("updated");
    }

    @Test
    void delete() {
        Course course = Course.fake("updated");

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.delete(1L);

        assertThat(courseDto.getTitle()).isEqualTo(null);
    }

    @Test
    void deleteSkill() {
        Course course = Course.fake("updated");

        CourseUpdateRequestDto courseUpdateRequestDto = new CourseUpdateRequestDto(
                "updated", "category", "description", "thumbnailPath", "", "초급", "skill", 0);

        course.update(courseUpdateRequestDto);

        assertThat(course.skillSets()).hasSize(1);

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        CourseDto courseDto = courseService.deleteSkill(new CourseId(1L), new HashTag("skill"));

        assertThat(courseDto.getSkillSets()).hasSize(0);
    }
}
