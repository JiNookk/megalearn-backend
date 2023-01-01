package jinookk.ourlms.models;

import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {
    @Test
    void convertToMyCourseDto() {
        Course course = Course.fake("Test");

        MyCourseDto myCourseDto = course.toMyCourseDto();

        assertThat(myCourseDto).isNotNull();
    }

    @Test
    void averageRating() {
        Course course = Course.fake("course");

        List<Rating> ratings = List.of(
                new Rating(1L, new AccountId(1L), new CourseId(1L), new Name("name1"), new Content("content1"),
                        LocalDateTime.now(), 4.0),
                new Rating(2L, new AccountId(2L), new CourseId(1L), new Name("name2"), new Content("content2"),
                        LocalDateTime.now(), 4.3),
                new Rating(3L, new AccountId(3L), new CourseId(1L), new Name("name3"), new Content("content3"),
                        LocalDateTime.now(), 3.7)
        );
        Double rating = course.averageRating(ratings);

        assertThat(rating).isEqualTo(4.0);
    }

    @Test
    void averageRatingWithBlankList() {
        Course course = Course.fake("course");

        List<Rating> ratings = List.of();
        Double rating = course.averageRating(ratings);

        assertThat(rating).isEqualTo(0.0);
    }

    @Test
    void convertToMonthlyPaymentDto() {
        Course course = Course.fake("course");

        List<Payment> payments = List.of(
                new Payment(1L, new CourseId(1L), new AccountId(1L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now()),
                new Payment(2L, new CourseId(1L), new AccountId(2L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now()),
                new Payment(3L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.now())
        );

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(105_000);
    }

    @Test
    void convertToMonthlyPaymentDtoWithOverdated() {
        Course course = Course.fake("course");

        List<Payment> payments = List.of(
                new Payment(4L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.of(2020, 1, 1, 1, 1)),
                new Payment(5L, new CourseId(1L), new AccountId(3L), new Price(35_000),
                        new Title("courseTitle1"), new Name("purchaser"), LocalDateTime.of(2020, 1, 1, 1, 1))
        );

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(0);
    }

    @Test
    void convertToMonthlyPaymentDtoWithBlankList() {
        Course course = Course.fake("course");

        List<Payment> payments = List.of();

        MonthlyPaymentDto monthlyPaymentDto = course.toMonthlyPaymentDto(payments);

        assertThat(monthlyPaymentDto.getCost()).isEqualTo(0);
    }

    @Test
    void filterIdWithNullValue() {
        Course course = Course.fake("fake");

        assertThat(course.filterId(new CourseId(null))).isTrue();
    }

    @Test
    void filterIdWithCorrectValue() {
        Course course = Course.fake("fake");

        assertThat(course.filterId(new CourseId(1L))).isTrue();
    }

    @Test
    void filterIdWithIncorrectValue() {
        Course course = Course.fake("fake");

        assertThat(course.filterId(new CourseId(10L))).isFalse();
    }
}
