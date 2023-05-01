package jinookk.ourlms.applications.comment;

import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CommentNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CommentRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeleteCommentService {
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    public DeleteCommentService(CommentRepository commentRepository,
                                AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
    }

    public CommentDeleteDto delete(Long commentId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));

        comment.removeAllProperties(new AccountId(account.id()));

        return comment.toCommentDeleteDto();
    }
}
