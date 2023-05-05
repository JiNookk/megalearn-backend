package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteCourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;

    public DeleteCourseService(CourseRepository courseRepository,
                               AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public CourseDto delete(Long courseId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(courseId));

        AccountId accountId = new AccountId(account.id());

        course.delete(accountId);

        return course.toCourseDto(accountId);
    }

    public CourseDto deleteSkill(CourseId courseId, HashTag hashTag, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = courseRepository.findById(courseId.value())
                .orElseThrow(() -> new CourseNotFound(courseId.value()));

        course.deleteSkill(hashTag, new AccountId(account.id()));

        return course.toCourseDto();
    }
}
