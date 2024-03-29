package jinookk.ourlms.applications.comment;

import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CommentRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class CreateCommentServiceTest extends Fixture {
    CreateCommentService createCommentService;
    CommentRepository commentRepository;
    InquiryRepository inquiryRepository;
    AccountRepository accountRepository;
    CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        commentRepository = mock(CommentRepository.class);
        courseRepository = mock(CourseRepository.class);
        createCommentService = new CreateCommentService(commentRepository, inquiryRepository, accountRepository, courseRepository);

        Comment comment = Fixture.comment("hi");
        Comment comment2 = Fixture.comment("hi2");
        Comment comment3 = Fixture.comment("hi3");
        given(commentRepository.save(any())).willReturn(comment);

        given(commentRepository.findAllByInquiryId(new InquiryId(1L))).willReturn(List.of(comment, comment2, comment3));

        Course course = Fixture.course("course");
        given(courseRepository.findById(any())).willReturn(Optional.of(course));

        Inquiry inquiry = Fixture.inquiry("inquiry");
        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry));

        Account account = Fixture.account("tester2");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void createWithExistingUserId() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "hi");

        CommentDto commentDto = createCommentService.create(commentRequestDto, new UserName("userName@email.com"));

        assertThat(commentDto).isNotNull();

        assertThat(commentDto.getAuthor()).isEqualTo("1st Tester");
    }

    @Test
    void createWithNonExistingUserId() {
        InquiryId inquiryId = new InquiryId(1L);
        Comment comment = new Comment(1L, new InquiryId(1L), new AccountId(2L), new Status(Status.CREATED),
                new Name("tester2", false), new Content("hi"), LocalDateTime.now());

        given(commentRepository.save(any())).willReturn(comment);
        CommentRequestDto commentRequestDto = new CommentRequestDto(inquiryId, "hi");

        UserName userName = new UserName("userName@email.com");
        CommentDto commentDto = createCommentService.create(commentRequestDto, userName);

        assertThat(commentDto).isNotNull();

        assertThat(commentDto.getAuthor()).isEqualTo("tester2");

        verify(commentRepository).findAllByInquiryId(inquiryId);
    }
}
