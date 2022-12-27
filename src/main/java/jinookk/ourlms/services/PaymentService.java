package jinookk.ourlms.services;

import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;

    public PaymentService(PaymentRepository paymentRepository, CourseRepository courseRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
    }

    public PaymentsDto list(AccountId accountId, CourseId courseId) {
        List<Course> courses = getCourses(accountId, courseId);

        List<PaymentDto> paymentDtos = courses.stream()
                .map(course -> paymentRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    public MonthlyPaymentsDto monthlyList(AccountId accountId) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<MonthlyPaymentDto> monthlyPaymentDtos = courses.stream()
                .map(course -> course.toMonthlyPaymentDto(
                        paymentRepository.findAllByCourseId(new CourseId(course.id()))))
                .toList();

        return new MonthlyPaymentsDto(monthlyPaymentDtos);
    }

    private List<Course> getCourses(AccountId accountId, CourseId courseId) {
        if (courseId.value() == null || courseId.value() < 1) {
            return courseRepository.findAllByAccountId(accountId);
        }

        return courseRepository.findAllByAccountId(accountId).stream()
                .filter(course -> course.filterId(courseId))
                .toList();
    }
}
