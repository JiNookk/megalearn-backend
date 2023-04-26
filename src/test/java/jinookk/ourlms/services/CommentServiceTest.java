package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.Name;
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

class CommentServiceTest {
    CommentService commentService;
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
        commentService = new CommentService(commentRepository, inquiryRepository, accountRepository, courseRepository);

        Comment comment = Comment.fake("hi");
        Comment comment2 = Comment.fake("hi2");
        Comment comment3 = Comment.fake("hi3");
        given(commentRepository.save(any())).willReturn(comment);

        given(commentRepository.findAllByInquiryId(new InquiryId(1L))).willReturn(List.of(comment, comment2, comment3));

        Course course = Course.fake("course");
        given(courseRepository.findById(any())).willReturn(Optional.of(course));

        Inquiry inquiry = Inquiry.fake("inquiry");
        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry));

        Account account = Account.fake("tester2");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void list() {
        CommentsDto commentsDto = commentService.list(new InquiryId(1L), new UserName("userName@email.com"));

        assertThat(commentsDto.getComments()).hasSize(3);
    }

    @Test
    void createWithExistingUserId() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(new InquiryId(1L), "hi");

        CommentDto commentDto = commentService.create(commentRequestDto, new UserName("userName@email.com"));

        assertThat(commentDto).isNotNull();

        assertThat(commentDto.getAuthor()).isEqualTo("1st Tester");
    }

    @Test
    void createWithNonExistingUserId() {
        InquiryId inquiryId = new InquiryId(1L);
        Comment comment = new Comment(1L, new InquiryId(1L), new AccountId(2L), new Status(Status.CREATED),
                new Name("tester2"), new Content("hi"), LocalDateTime.now());

        given(commentRepository.save(any())).willReturn(comment);
        CommentRequestDto commentRequestDto = new CommentRequestDto(inquiryId, "hi");

        UserName userName = new UserName("userName@email.com");
        CommentDto commentDto = commentService.create(commentRequestDto, userName);

        assertThat(commentDto).isNotNull();

        assertThat(commentDto.getAuthor()).isEqualTo("tester2");

        verify(commentRepository).findAllByInquiryId(inquiryId);
    }

    @Test
    void update() {
        Comment comment = spy(Comment.fake("hi"));

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        commentService.update(1L, "addItem", new UserName("userName"));

        verify(commentRepository).findById(1L);

        verify(comment).updateContent("addItem", new AccountId(1L));
    }

    @Test
    void delete() {
        Comment comment = spy(Comment.fake("hi"));

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        commentService.delete(1L, new UserName("userName"));

        verify(commentRepository).findById(1L);

        verify(comment).removeAllProperties(new AccountId(1L));
    }
}
