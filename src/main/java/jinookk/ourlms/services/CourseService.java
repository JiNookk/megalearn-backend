package jinookk.ourlms.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.entities.QCourse;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final LikeRepository likeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CourseService(CourseRepository courseRepository, AccountRepository accountRepository,
                         LikeRepository likeRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.likeRepository = likeRepository;
    }

    public CourseDto create(CourseRequestDto courseRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = Course.of(courseRequestDto, account.name(), new AccountId(account.id()));

        Course saved = courseRepository.save(course);

        return saved.toCourseDto();
    }

    // TODO : 강의에서 account여부와 payment여부를 분리해야한다.
    public CourseDto detail(UserName userName, CourseId courseId) {
        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        Optional<Account> account = accountRepository.findByUserName(userName);

        return course.toCourseDto(account);
    }

    public CoursesDto list(Integer page, CourseFilterDto courseFilterDto) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder(course.status.value.ne(Status.DELETED));

        if (courseFilterDto.getLevel() != null) {
            builder.or(course.level.eq(Level.of(courseFilterDto.getLevel())));
        }


        if (courseFilterDto.getCost() != null) {
            BooleanExpression costExpression = courseFilterDto.getCost().equals("무료")
                    ? course.price.value.eq(0)
                    : course.price.value.gt(0);

            builder.or(costExpression);
        }

        if (courseFilterDto.getSkill() != null) {
            builder.or(course.skillSets.contains(new HashTag(courseFilterDto.getSkill())));
        }

        if (courseFilterDto.getContent() != null) {
            builder.and(course.title.value.like("%" + courseFilterDto.getContent() + "%"))
                    .or(course.description.value.like("%" + courseFilterDto.getContent() + "%"))
                    .or(course.goals.contains(new Content("%" + courseFilterDto.getContent() + "%")));
        }

        JPAQuery<Course> courseQuery = queryFactory.selectFrom(course)
                .where(builder);

        int pageSize = 24;
        long totalItems = courseQuery.fetch().size();

        List<Course> courses = courseQuery
                .offset((long) (page - 1) * pageSize)
                .limit(pageSize)
                .fetch();

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return new CoursesDto(courseDtos, totalPages);
    }

    public CoursesDto wishList(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        List<Like> likes = likeRepository.findAllByAccountId(new AccountId(account.id()));

        List<Long> courseIds = likes.stream()
                .filter(Like::clicked)
                .map(like -> like.courseId().value())
                .toList();

        List<CourseDto> courseDtos = courseRepository.findAllById(courseIds).stream()
                .filter(course -> !course.status().equals(new Status(Status.DELETED)))
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos);
    }

    public CoursesDto listForAdmin(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 6);

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QCourse course = QCourse.course;

        JPAQuery<Course> courseJPAQuery = queryFactory.selectFrom(course)
                .where(course.status.value.ne(Status.DELETED));


        List<Course> courses = courseJPAQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(course.createdAt.desc())
                .fetch();


        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        int totalItems = courseJPAQuery.fetch().size();

        int totalPages = (int) Math.ceil((double) totalItems / pageable.getPageSize());

        return new CoursesDto(courseDtos, totalPages);
    }

    public CourseDto update(Long courseId, CourseUpdateRequestDto courseUpdateRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.update(courseUpdateRequestDto, new AccountId(account.id()));

        return course.toCourseDto();
    }

    public CourseDto delete(Long courseId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.delete(new AccountId(account.id()));

        return course.toCourseDto();
    }

    public CourseDto deleteSkill(CourseId courseId, HashTag hashTag, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        course.deleteSkill(hashTag, new AccountId(account.id()));

        return course.toCourseDto();
    }

    public CourseDto updateStatus(Long courseId, StatusUpdateDto statusUpdateDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        Course updated = course.updateStatus(statusUpdateDto);

        return updated.toCourseDto();
    }
}
