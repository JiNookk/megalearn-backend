package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MyCourseServiceTest {
    MyCourseService myCourseService;
    CourseRepository courseRepository;
    PaymentRepository paymentRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        courseRepository = mock(CourseRepository.class);
        myCourseService = new MyCourseService(courseRepository, paymentRepository, accountRepository);

        Payment payment = Payment.fake(35_000);
        given(paymentRepository.findAllByAccountId(any())).willReturn(List.of(payment));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void purchasedList() {
        Course course = Course.fake("test");

        given(courseRepository.findAllById(any()))
                .willReturn(List.of(course));

        CoursesDto list = myCourseService.myCourses(new UserName("userName@email.com"));

        assertThat(list.getCourses()).hasSize(1);
    }

    @Test
    void uploadedList() {
        Course course = Course.fake("test");

        given(courseRepository.findAllByAccountId(new AccountId(1L)))
                .willReturn(List.of(course));

        CoursesDto uploadedList = myCourseService.uploadedList(new UserName("userName@email.com"), Status.PROCESSING);

        assertThat(uploadedList.getCourses()).hasSize(1);
    }
}
