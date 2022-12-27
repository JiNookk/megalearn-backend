package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;

    public CourseService(CourseRepository courseRepository, AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public CourseDto detail(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFound(courseId));

        return course.toCourseDto();
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

    public CoursesDto list() {
        List<Course> courses = courseRepository.findAll();

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos);
    }

    public CourseDto delete(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.delete();

        return course.toCourseDto();
    }
}
