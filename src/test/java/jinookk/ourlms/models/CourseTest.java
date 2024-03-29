package jinookk.ourlms.models;

import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.enums.PaymentStatus;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseTest extends Fixture {
    @Test
    void convertToMyCourseDto() {
        Course course = Fixture.course("Test");

        MyCourseDto myCourseDto = course.toMyCourseDto();

        assertThat(myCourseDto).isNotNull();
    }

    @Test
    void averageRating() {
        Course course = Fixture.course("course");

        List<Rating> ratings = List.of(
                new Rating(1L, new AccountId(1L), new CourseId(1L), new Name("name1", false), new Content("content1"), 4.0),
                new Rating(2L, new AccountId(2L), new CourseId(1L), new Name("name2", false), new Content("content2"), 4.3),
                new Rating(3L, new AccountId(3L), new CourseId(1L), new Name("name3", false), new Content("content3"), 3.7)
        );

        Double rating = course.averageRating(ratings);

        assertThat(rating).isEqualTo(4.0);
    }

    @Test
    void averageRatingWithBlankList() {
        Course course = Fixture.course("course");

        List<Rating> ratings = List.of();
        Double rating = course.averageRating(ratings);

        assertThat(rating).isEqualTo(0.0);
    }

    @Test
    void convertToMonthlyPaymentDto() {
        Course course = Fixture.course("course");

        List<Payment> payments = List.of(
                new Payment(1L, new CourseId(1L), new AccountId(1L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS, LocalDateTime.now()),
                new Payment(2L, new CourseId(1L), new AccountId(2L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS, LocalDateTime.now()),
                new Payment(3L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS, LocalDateTime.now())
        );

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(105_000);
    }

    @Test
    void convertToMonthlyPaymentDtoWithOverdated() {
        Course course = Fixture.course("course");

        List<Payment> payments = List.of(
                new Payment(4L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS, LocalDateTime.of(2020, 1, 1, 1, 1)),
                new Payment(5L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser", false), PaymentStatus.SUCCESS, LocalDateTime.of(2020, 1, 1, 1, 1))
        );

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(0);
    }

    @Test
    void convertToMonthlyPaymentDtoWithBlankList() {
        Course course = Fixture.course("course");

        List<Payment> payments = List.of();

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(0);
    }

    @Test
    void filterIdWithNullValue() {
        Course course = Fixture.course("fake");

        assertThat(course.filterId(new CourseId(null))).isTrue();
    }

    @Test
    void filterIdWithCorrectValue() {
        Course course = Fixture.course("fake");

        assertThat(course.filterId(new CourseId(1L))).isTrue();
    }

    @Test
    void filterIdWithIncorrectValue() {
        Course course = Fixture.course("fake");

        assertThat(course.filterId(new CourseId(10L))).isFalse();
    }

    @Test
    void validatePayment() {
        Course course = Fixture.course("fake");

        Payment payment = Fixture.payment(35_000);

        boolean validatePayment = course.validatePayment(Optional.of(payment));

        assertThat(validatePayment).isTrue();
    }

    @Test
    void validatePaymentWithNull() {
        Course course = Fixture.course("fake");

        boolean validatePayment = course.validatePayment(Optional.empty());

        assertThat(validatePayment).isFalse();
    }

    @Test
    void validateInstructor() {
        Course course = Fixture.course("fake");

        boolean validateInstructor = course.isInstructor(new AccountId(1L));

        assertThat(validateInstructor).isTrue();
    }

    @Test
    void validateInstructorWithIncorrectId() {
        Course course = Fixture.course("fake");

        assertThat(course.isInstructor(new AccountId(2L))).isFalse();
        assertThat(course.isInstructor(new AccountId(null))).isFalse();
        assertThat(course.isInstructor(null)).isFalse();
    }

    @Test
    void convertToDtoWithPayment() {
        Course course = Fixture.course("course");

        Account account = Fixture.account("account");

        CourseDto courseDto = course.toCourseDto(new AccountId(account.id()));

        assertThat(courseDto.getPurchased()).isEqualTo(true);
    }

    @Test
    void convertToDtoWithPaymentNull() {
        Course course = Fixture.course("course");

        course.updateStatus("approved");

        CourseDto courseDto = course.toCourseDto();

        assertThat(courseDto.getPurchased()).isEqualTo(false);
    }

    @Test
    void fake() {
        Course course = Fixture.course("fake");

        assertThat(course.level()).isEqualTo(Level.TOBEDETERMINED);
        assertThat(course.imagePath()).isEqualTo(new ImagePath("imagePath"));
    }

    @Test
    void changeLevel() {
        Course course = Fixture.course("fake");

        assertThat(course.level()).isEqualTo(Level.TOBEDETERMINED);

        Course changed = course.changeLevel(Level.EXPERT);

        assertThat(changed.level()).isEqualTo(Level.EXPERT);
    }

    @Test
    void updateStatus() {
        Course course = Fixture.course("fake");

        course.updateStatus("processing");

        assertThat(course.status().toString()).isEqualTo(CourseStatus.PROCESSING.toString());

        Course approved = course.updateStatus("approved");

        assertThat(approved.status().toString()).isEqualTo(CourseStatus.APPROVED.toString());
    }

    @Test
    void isApproved() {
        Course course = Fixture.course("fake");

        course.updateStatus("processing");

        assertThat(course.isApproved()).isFalse();

        course.updateStatus("approved");

        assertThat(course.isApproved()).isTrue();
    }

    @Test
    void validateAuthorityWithInstructorId() {
        Course course = Fixture.course("fake");

        course.updateStatus("processing");
        assertDoesNotThrow(() -> {
            course.validateAuthority(new AccountId(1L));
        });

        course.updateStatus("submitted");
        assertDoesNotThrow(() -> {
            course.validateAuthority(new AccountId(1L));
        });

        course.updateStatus("approved");
        assertDoesNotThrow(() -> {
            course.validateAuthority(new AccountId(1L));
        });
    }

    @Test
    void validateAuthorityWithOtherId() {
        Course course = Fixture.course("fake");

        course.updateStatus("processing");
        assertThrows(AccessDeniedException.class, () -> {
            course.validateAuthority(new AccountId(2L));
        });

        course.updateStatus("submitted");
        assertThrows(AccessDeniedException.class, () -> {
            course.validateAuthority(new AccountId(2L));
        });

        course.updateStatus("approved");
        assertDoesNotThrow(() -> {
            course.validateAuthority(new AccountId(2L));
        });
    }
}
