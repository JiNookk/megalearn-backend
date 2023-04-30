package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.exceptions.RatingNotExisting;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RatingTest {
    @Test
    void createdFromDto() {
        Account account = Account.fake("name");
        RatingRequestDto ratingRequestDto = new RatingRequestDto(1L, 4, "content");

        Rating rating = Rating.of(account, ratingRequestDto);

        assertThat(rating.id()).isEqualTo(null);
        assertThat(rating.content()).isEqualTo(new Content("content"));
        assertThat(rating.accountId().value()).isEqualTo(1L);
        assertThat(rating.courseId()).isEqualTo(new CourseId(1L));
        assertThat(rating.author()).isEqualTo(new Name("name", false));
    }

    @Test
    void calculateAverageWithCorrectValue() {
        List<CourseId> courseIds = List.of(new CourseId(1L), new CourseId(2L));

        List<Rating> ratings = List.of(
                Rating.fake(5.0),
                Rating.fake(4.0),
                Rating.fake(3.0)
        );

        Double rating = Rating.averageOf(courseIds, ratings);

        assertThat(rating).isEqualTo(4.0);
    }

    @Test
    void calculateAverageWithInvalidList() {
        List<CourseId> courseIds = List.of(new CourseId(1L), new CourseId(2L));

        List<Rating> ratings = List.of();

        assertThrows(RatingNotExisting.class, () -> {
            Rating.averageOf(courseIds, ratings);
        });

        assertThrows(RatingNotExisting.class, () -> {
            Rating.averageOf(courseIds, null);
        });
    }
}
