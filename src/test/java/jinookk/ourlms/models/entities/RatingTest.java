package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(rating.author()).isEqualTo(new Name("name"));
    }
}