package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class CreateCourseService {
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CreateCourseService(CourseRepository courseRepository,
                               AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public CourseDto create(CourseRequestDto courseRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Course course = Course.of(courseRequestDto, account.name(), new AccountId(account.id()));

        Course saved = courseRepository.save(course);

        return saved.toCourseDto();
    }
}
