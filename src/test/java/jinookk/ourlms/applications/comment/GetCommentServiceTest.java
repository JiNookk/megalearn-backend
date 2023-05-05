package jinookk.ourlms.applications.comment;

import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCommentServiceTest extends Fixture {
    GetCommentService getCommentService;
    CommentRepository commentRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        commentRepository = mock(CommentRepository.class);
        getCommentService = new GetCommentService(commentRepository, accountRepository);

        Comment comment = Fixture.comment("hi");
        Comment comment2 = Fixture.comment("hi2");
        Comment comment3 = Fixture.comment("hi3");
        given(commentRepository.save(any())).willReturn(comment);

        given(commentRepository.findAllByInquiryId(new InquiryId(1L))).willReturn(List.of(comment, comment2, comment3));

        Account account = Fixture.account("tester2");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void list() {
        CommentsDto commentsDto = getCommentService.list(new InquiryId(1L), new UserName("userName@email.com"));

        assertThat(commentsDto.getComments()).hasSize(3);
    }
}
