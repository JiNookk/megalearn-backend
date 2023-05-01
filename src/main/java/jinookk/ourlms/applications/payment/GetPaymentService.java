package jinookk.ourlms.applications.payment;

import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MonthlyPaymentsDto;
import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.dtos.PaymentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class GetPaymentService {
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;

    public GetPaymentService(PaymentRepository paymentRepository,
                             CourseRepository courseRepository,
                             AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
    }

    public PaymentsDto list() {
        return new PaymentsDto(paymentRepository.findAll().stream()
                .map(Payment::toDto)
                .toList());
    }

    public PaymentsDto list(UserName userName, CourseId courseId) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = getCourses(accountId, courseId);

        List<PaymentDto> paymentDtos = courses.stream()
                .map(course -> paymentRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
    }

    public MonthlyPaymentsDto monthlyList(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<MonthlyPaymentDto> monthlyPaymentDtos = courses.stream()
                .map(course -> course.toMonthlyPaymentDto(
                        paymentRepository.findAllByCourseId(new CourseId(course.id()))))
                .toList();

        return new MonthlyPaymentsDto(monthlyPaymentDtos);
    }

    public PaymentsDto list(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Payment> payments = paymentRepository.findAllByAccountId(accountId);

        List<PaymentDto> paymentDtos = payments.stream()
                .map(Payment::toDto)
                .toList();

        return new PaymentsDto(paymentDtos);
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
