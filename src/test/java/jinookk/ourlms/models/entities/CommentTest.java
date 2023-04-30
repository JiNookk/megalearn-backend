package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {
    Inquiry inquiry;

    @BeforeEach
    void setup() {
        inquiry = new Inquiry(
                1L, new CourseId(1L), new LectureId(2L), new AccountId(3L), List.of(new HashTag("tag")),
                new Title("title"), new Content("content"), new LectureTime(1, 24),
                new Name("post publisher", false), false, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createdFromDto() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "content");
        Name publisher = new Name("name", false);
        AccountId accountId = new AccountId(1L);

        Comment comment = Comment.of(commentRequestDto, publisher, accountId);

        assertThat(comment.id()).isEqualTo(null);
        assertThat(comment.content()).isEqualTo(new Content("content"));
        assertThat(comment.status()).isEqualTo(new Status(Status.CREATED));
    }

    @Test
    void removeAllProperties() {
        Comment comment = Comment.fake("hi");

        comment.removeAllProperties(new AccountId(1L));

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

    @Test
    void createCommentWithSameAccountIdWithInquiry() {
        Account account = new Account(new Name("tester", false), new UserName("userName@email.com"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");
        List<Comment> comments = List.of(Comment.fake("hi"));
        Course course = Course.fake("course");

        Comment comment = Comment.of(inquiry, comments, commentRequestDto, account, course);

        assertThat(comment).isNotNull();
//        assertThat(comment.author()).isEqualTo(new Name("post publisher"));
    }

    @Test
    void createCommentWithPreviousCommentAndNotPublisher() {
        Account account = new Account(new Name("tester", false), new UserName("userName@email.com"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");

        Comment commentWithFirstAccountId = Comment.fake("hi");
        List<Comment> comments = List.of(commentWithFirstAccountId);
        Course course = Course.fake("course");

        Comment comment = Comment.of(inquiry, comments, commentRequestDto, account, course);

        assertThat(comment).isNotNull();
//        assertThat(comment.author()).isEqualTo(new Name("1st Tester"));
    }

    @Test
    void createCommentWithoutPreviousCommentAndNotPublisher() {
        Account account = new Account(new Name("2nd Tester", false), new UserName("userName@email.com"));
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "comment");

        Comment commentWithThirdAccountId = new Comment(1L, new InquiryId(2L), new AccountId(3L), new Status(Status.CREATED),
                new Name("3rd Author", false), new Content("content"), LocalDateTime.now());

        List<Comment> comments = List.of(commentWithThirdAccountId);
        Course course = Course.fake("course");

        Comment comment = Comment.of(inquiry, comments, commentRequestDto, account, course);

        assertThat(comment).isNotNull();
        assertThat(comment.author()).isEqualTo(new Name("2nd Tester", false));
    }

}
