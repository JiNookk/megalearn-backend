package jinookk.ourlms.models.entities;

import jinookk.ourlms.models.vos.AccountId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {
    @Test
    void removeAllProperties() {
        Comment comment = Comment.fake("hi");

        comment.removeAllProperties();

        assertThat(comment.content()).isNull();
        assertThat(comment.inquiryId()).isNull();
        assertThat(comment.publishTime()).isNull();
    }

    @Test
    void isMyComment() {
        Comment comment = Comment.fake("hi");

        assertThat(comment.accountId()).isEqualTo(new AccountId(1L));

        assertThat(comment.isMyComment(new AccountId(1L))).isTrue();
        assertThat(comment.isMyComment(new AccountId(2L))).isFalse();
    }
}
