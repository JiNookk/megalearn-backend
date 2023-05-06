package jinookk.ourlms.applications.course;

import jinookk.ourlms.dtos.GetCoursesDto;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MyCourseService {
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public MyCourseService(CourseRepository courseRepository, PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public GetCoursesDto myCourses(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        List<Payment> payments = paymentRepository.findAllByAccountId(new AccountId(account.id()));

        List<Long> courseIds = payments.stream()
                .map(payment -> payment.courseId().value())
                .toList();

        List<Course> courses = courseRepository.findAllById(courseIds);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new GetCoursesDto(courseDtos);
    }

    public GetCoursesDto uploadedList(UserName userName, String type) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        List<Course> courses = courseRepository.findAllByAccountId(new AccountId(account.id()));

        List<Course> filtered = filtered(courses, type);

        List<CourseDto> courseDtos = filtered.stream()
                .map(Course::toCourseDto)
                .toList();

        return new GetCoursesDto(courseDtos);
    }

    private List<Course> filtered(List<Course> courses, String type) {
        return courses.stream()
                .filter(course -> course.filterType(type))
                .toList();
    }
}
