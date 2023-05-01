package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;

    public UpdateCourseService(CourseRepository courseRepository,
                               AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public CourseDto update(Long courseId, CourseUpdateRequestDto courseUpdateRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        course.update(courseUpdateRequestDto, new AccountId(account.id()));

        return course.toCourseDto();
    }

    public CourseDto updateStatus(Long courseId, StatusUpdateDto statusUpdateDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        Course updated = course.updateStatus(statusUpdateDto);

        return updated.toCourseDto();
    }
}
