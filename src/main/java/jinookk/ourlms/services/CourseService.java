package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import jinookk.ourlms.specifications.CourseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PaymentRepository paymentRepository;

    public CourseService(CourseRepository courseRepository, AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    public CourseDto detail(AccountId accountId, CourseId courseId) {
        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(()-> new CourseNotFound(courseId.value()));

        Optional<Payment> payment = paymentRepository.findByAccountIdAndCourseId(accountId, courseId);

        CourseDto courseDto = course.toCourseDto(payment, accountId);

        return courseDto;
    }

    public CourseDto create(CourseRequestDto courseRequestDto, AccountId accountId) {
        Account account = accountRepository.findById(accountId.value())
                .orElseThrow(() -> new AccountNotFound(accountId));

        Course course = Course.of(courseRequestDto, account.name(), accountId);

        Course saved = courseRepository.save(course);

        return saved.toCourseDto();
    }

    public CourseDto update(Long courseId, CourseUpdateRequestDto courseUpdateRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.update(courseUpdateRequestDto);

        return course.toCourseDto();
    }

    public CoursesDto list(Integer page, CourseFilterDto courseFilterDto) {
        Pageable pageable = PageRequest.of(page -1, 24);

        Specification<Course> spec = (root, query, criteriaBuilder) -> null;

        if (courseFilterDto.getLevel() != null) {
            spec = spec.and(CourseSpecification.equalLevel(Level.of(courseFilterDto.getLevel())));
        }

        if (courseFilterDto.getCost() != null) {
            spec = spec.and(CourseSpecification.equalCost(courseFilterDto.getCost()));
        }

        if (courseFilterDto.getSkill()!= null) {
            spec = spec.and(CourseSpecification.equalSkills(courseFilterDto.getSkill()));
        }

        if (courseFilterDto.getContent()!= null) {
            System.out.println(courseFilterDto.getContent());

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
}
