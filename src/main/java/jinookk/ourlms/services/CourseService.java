package jinookk.ourlms.services;

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
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import jinookk.ourlms.specifications.CourseSpecification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final LikeRepository likeRepository;

    public CourseService(CourseRepository courseRepository, AccountRepository accountRepository,
                         LikeRepository likeRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.likeRepository = likeRepository;
    }

    public CourseDto create(CourseRequestDto courseRequestDto, Name userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = Course.of(courseRequestDto, account.name(), new AccountId(account.id()));

        Course saved = courseRepository.save(course);

        return saved.toCourseDto();
    }

    // TODO : 강의에서 account여부와 payment여부를 분리해야한다.
    public CourseDto detail(Name userName, CourseId courseId) {
        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        Optional<Account> account = accountRepository.findByUserName(userName);

        return course.toCourseDto(account);
    }

//    @Cacheable(value = "redisCache", key = "#courses")
    @Cacheable("courses")
    public CoursesDto list(Integer page, CourseFilterDto courseFilterDto) {
        Pageable pageable = PageRequest.of(page - 1, 24);

        Specification<Course> spec = Specification.where(CourseSpecification.notEqualDeleted());

        if (courseFilterDto.getLevel() != null) {
            spec = spec.and(CourseSpecification.equalLevel(Level.of(courseFilterDto.getLevel())));
        }

        if (courseFilterDto.getCost() != null) {
            spec = spec.and(CourseSpecification.equalCost(courseFilterDto.getCost()));
        }

        if (courseFilterDto.getSkill() != null) {
            spec = spec.and(CourseSpecification.equalSkills(courseFilterDto.getSkill()));
        }

        if (courseFilterDto.getContent() != null) {
            // join시 일치하는 모든 칼럼을 가져온다.
            // elementCollection의 값 하나당 하나의 칼럼을 가져옴
            // 어떻게 막을 수 있을까?

            spec = spec.and(
                    CourseSpecification.likeTitle(courseFilterDto.getContent())
                            .or(CourseSpecification.likeContent(courseFilterDto.getContent()))
//                    .or(CourseSpecification.likeGoals(courseFilterDto.getContent()))
            );
        }

        Page<Course> courses = courseRepository.findAll(spec, pageable);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos, courses.getTotalPages());
    }

    public CoursesDto wishList(Name userName) {
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
        Sort sort = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page - 1, 6, sort);

        Specification<Course> spec = Specification.where(CourseSpecification.notEqualDeleted());

        Page<Course> courses = courseRepository.findAll(spec, pageable);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos, courses.getTotalPages());
    }

    public CourseDto update(Long courseId, CourseUpdateRequestDto courseUpdateRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.update(courseUpdateRequestDto);

        return course.toCourseDto();
    }

    public CourseDto delete(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.delete();

        return course.toCourseDto();
    }

    public CourseDto deleteSkill(CourseId courseId, HashTag hashTag) {
        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        course.deleteSkill(hashTag);

        return course.toCourseDto();
    }

    public CourseDto updateStatus(Long courseId, StatusUpdateDto statusUpdateDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        Course updated = course.updateStatus(statusUpdateDto);

        return updated.toCourseDto();
    }
}
