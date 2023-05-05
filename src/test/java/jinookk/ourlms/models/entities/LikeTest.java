package jinookk.ourlms.models.entities;

import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LikeTest {
    @Test
    void toggle() {
        Like like = Fixture.like(false);

        assertThat(like.clicked()).isEqualTo(false);

        Like toggled = like.toggle();

        assertThat(toggled.clicked()).isEqualTo(true);
    }

    @Test
    void createdFromDto() {
        Like like = Like.of(new AccountId(1L), new CourseId(1L));

        assertThat(like.id()).isEqualTo(null);
        assertThat(like.accountId()).isEqualTo(new AccountId(1L));
        assertThat(like.courseId()).isEqualTo(new CourseId(1L));
    }

    @Test
    void collectionFromDto() {
        List<Like> likes = Like.listOf(new AccountId(1L), List.of(new CourseId(1L)));

        assertThat(likes.size()).isEqualTo(1);
    }
}
