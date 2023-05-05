package jinookk.ourlms.applications.comment;

import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class DeleteCommentServiceTest extends Fixture {
    DeleteCommentService deleteCommentService;
    CommentRepository commentRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        commentRepository = mock(CommentRepository.class);
        deleteCommentService = new DeleteCommentService(commentRepository, accountRepository);

        Comment comment = Fixture.comment("hi");
        Comment comment2 = Fixture.comment("hi2");
        Comment comment3 = Fixture.comment("hi3");
        given(commentRepository.save(any())).willReturn(comment);

        given(commentRepository.findAllByInquiryId(new InquiryId(1L))).willReturn(List.of(comment, comment2, comment3));

        Account account = Fixture.account("tester2");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void delete() {
        Comment comment = spy(Fixture.comment("hi"));

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        deleteCommentService.delete(1L, new UserName("userName"));

        verify(commentRepository).findById(1L);

        verify(comment).removeAllProperties(new AccountId(1L));
    }
}
