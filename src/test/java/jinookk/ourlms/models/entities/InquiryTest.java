package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
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
                1L, new CourseId(1L), new LectureId(2L), new AccountId(3L), List.of(new HashTag("tag")),
                 new Title("title"), new Content("content"), new LectureTime(1, 24),
                new Name("post publisher"), false, LocalDateTime.now(), LocalDateTime.now());

        commentWithThirdAccountId = new Comment(1L, new InquiryId(2L), new AccountId(3L), new Status(Status.CREATED),
                new Name("3rd Author"), new Content("content"), LocalDateTime.now());
    }

    @Test
    void createdFromDto() {
        InquiryRequestDto inquiryRequestDto = new InquiryRequestDto(
                new LectureId(1L), List.of(), "title", "content", false, 1, 31, 1L);

        Inquiry created = Inquiry.of(inquiryRequestDto, new AccountId(1L), new Name("name"));

        assertThat(created.id()).isEqualTo(null);
        assertThat(created.lectureId()).isEqualTo(new LectureId(1L));
        assertThat(created.hashTags()).isEqualTo(List.of());
        assertThat(created.content()).isEqualTo(new Content("content"));
        assertThat(created.lectureTime()).isEqualTo(new LectureTime(1, 31));
    }

    @Test
    void isPublisher() {
        assertThat(inquiry.isPublisherId(new AccountId(3L))).isTrue();
        assertThat(inquiry.isPublisherId(new AccountId(2L))).isFalse();
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

    @Test
    void toggleSolved() {
        Inquiry inquiry = Inquiry.fake("fake");

        assertThat(inquiry.status().solved()).isEqualTo("processing");

        Inquiry toggled = inquiry.toggleSolved();

        assertThat(toggled.status().solved()).isEqualTo("completed");
    }

    @Test
    void reply() {
        Inquiry inquiry = Inquiry.fake("fake");

        assertThat(inquiry.status().replied()).isEqualTo("processing");

        Inquiry replied = inquiry.reply();

        assertThat(replied.status().replied()).isEqualTo("completed");
    }

    @Test
    void increaseHits() {
        Inquiry fake = Inquiry.fake("fake");

        assertThat(fake.hits()).isEqualTo(0);

        Inquiry increased = fake.increaseHits();

        assertThat(increased.hits()).isEqualTo(1);
    }
}
