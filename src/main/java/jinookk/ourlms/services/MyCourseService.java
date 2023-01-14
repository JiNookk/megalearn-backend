package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.ids.AccountId;
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

    public MyCourseService(CourseRepository courseRepository, PaymentRepository paymentRepository) {
        this.courseRepository = courseRepository;
        this.paymentRepository = paymentRepository;
    }

    public CoursesDto myCourses(AccountId accountId) {
        List<Payment> payments = paymentRepository.findAllByAccountId(accountId);

        List<Long> courseIds = payments.stream()
                .map(payment -> payment.courseId().value())
                .toList();

        List<Course> courses = courseRepository.findAllById(courseIds);

        List<CourseDto> courseDtos = courses.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos);
    }

    public CoursesDto uploadedList(AccountId accountId, String type) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<Course> filtered = filtered(courses, type);

        List<CourseDto> courseDtos = filtered.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos);
    }

    private List<Course> filtered(List<Course> courses, String type) {
        return courses.stream()
                .filter(course -> course.filterType(type))
                .toList();
    }
}
