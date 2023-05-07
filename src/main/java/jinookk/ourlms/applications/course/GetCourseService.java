package jinookk.ourlms.applications.course;

import jinookk.ourlms.daos.CourseDao;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.GetCoursesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class GetCourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final LikeRepository likeRepository;
    private final CourseDao courseDao;

    @PersistenceContext
    private EntityManager entityManager;

    public GetCourseService(CourseRepository courseRepository, AccountRepository accountRepository,
                            LikeRepository likeRepository, CourseDao courseDao) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.likeRepository = likeRepository;
        this.courseDao = courseDao;
    }

    // TODO : 강의에서 account여부와 payment여부를 분리해야한다.
    public CourseDto detail(UserName userName, CourseId courseId) {
        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        if (userName == null) {
            course.validateAuthority(new AccountId(-1L));

            return course.toCourseDto();
        }

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        course.validateAuthority(new AccountId(account.id()));

        return course.toCourseDto(new AccountId(account.id()));
    }

    public CoursesDto list(Integer page, CourseFilterDto courseFilterDto) {
        Pageable pageable = PageRequest.of(page - 1, 24);

        Page<Course> courses = courseDao.findCoursesByFilter(courseFilterDto, pageable);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos, courses.getTotalPages());
    }

    public GetCoursesDto wishList(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        List<Like> likes = likeRepository.findAllByAccountId(new AccountId(account.id()));

        List<Long> courseIds = likes.stream()
                .filter(Like::clicked)
                .map(like -> like.courseId().value())
                .toList();

        List<CourseDto> courseDtos = courseRepository.findAllById(courseIds).stream()
                .filter(course -> course.status().equals(CourseStatus.APPROVED))
                .map(Course::toCourseDto)
                .toList();

        return new GetCoursesDto(courseDtos);
    }

    public CoursesDto listForAdmin(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 6);

        Page<Course> courses = courseDao.findCoursesForAdmin(pageable);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos, courses.getTotalPages());
    }
}
