package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.models.vos.Like;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.status.InquiryStatus;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.models.vos.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InquiryTest {
    Inquiry inquiry;
    Comment commentWithThirdAccountId;

    @BeforeEach
    void setup() {
        inquiry = new Inquiry(
                1L, new CourseId(1L), new LectureId(2L), new AccountId(3L), new InquiryStatus(Status.CREATED), List.of(new HashTag("tag")),
                List.of(new Like(1L)), new Title("title"), new Content("content"), new LectureTime(1L, 24L),
                new Name("post publisher"), false, LocalDateTime.now(), LocalDateTime.now());

        commentWithThirdAccountId = new Comment(1L, new InquiryId(2L), new AccountId(3L), new Status(Status.CREATED),
                new Name("3rd Author"), new Content("content"), LocalDateTime.now());
    }

    @Test
    void isPublisher() {
        assertThat(inquiry.isPublisherId(new AccountId(3L))).isTrue();
        assertThat(inquiry.isPublisherId(new AccountId(2L))).isFalse();
    }

    @Test
    void createCommentWithSameAccountIdWithInquiry() {
        Account account = new Account(3L, new Name("tester"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");
        List<Comment> comments = List.of(Comment.fake("hi"));

        Comment comment = inquiry.createComment(comments, commentRequestDto, account);

        assertThat(comment).isNotNull();
        assertThat(comment.author()).isEqualTo(new Name("post publisher"));
    }

    @Test
    void createCommentWithPreviousCommentAndNotPublisher() {
        Account account = new Account(1L, new Name("tester"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");

        Comment commentWithFirstAccountId = Comment.fake("hi");
        List<Comment> comments = List.of(commentWithFirstAccountId);

        Comment comment = inquiry.createComment(comments, commentRequestDto, account);

        assertThat(comment).isNotNull();
        assertThat(comment.author()).isEqualTo(new Name("1st Tester"));
    }

    @Test
    void createCommentWithoutPreviousCommentAndNotPublisher() {
        Account account = new Account(2L, new Name("2nd Tester"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");

        List<Comment> comments = List.of(commentWithThirdAccountId);

        Comment comment = inquiry.createComment(comments, commentRequestDto, account);

        assertThat(comment).isNotNull();
        assertThat(comment.author()).isEqualTo(new Name("2nd Tester"));
    }

    @Test
    void validatePreviousCommentWithSameAccountId() {
        AccountId accountId = new AccountId(3L);

        List<Comment> comments = List.of(commentWithThirdAccountId);

        Optional<Comment> comment = inquiry.previousComment(comments, accountId);

        assertThat(comment.isPresent()).isTrue();
    }

    @Test
    void validatePreviousCommentWithDifferentAccountId() {
        AccountId accountId = new AccountId(3L);

        List<Comment> comments = List.of(Comment.fake("hi"));

        Optional<Comment> comment = inquiry.previousComment(comments, accountId);

        assertThat(comment.isPresent()).isFalse();
    }
}
