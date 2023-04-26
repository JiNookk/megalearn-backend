package jinookk.ourlms.services;

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
public class CommentService {
    private final CommentRepository commentRepository;
    private final InquiryRepository inquiryRepository;
    private final AccountRepository accountRepository;
    private final CourseRepository courseRepository;

    public CommentService(CommentRepository commentRepository, InquiryRepository inquiryRepository,
                          AccountRepository accountRepository, CourseRepository courseRepository) {
        this.commentRepository = commentRepository;
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
        this.courseRepository = courseRepository;
    }

    public CommentsDto list(InquiryId inquiryId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Comment> comments = commentRepository.findAllByInquiryId(inquiryId);

        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> comment.toCommentDto(accountId))
                .toList();

        return new CommentsDto(commentDtos);
    }

    public CommentDto create(CommentRequestDto commentRequestDto, UserName userName) {
        InquiryId inquiryId = commentRequestDto.getInquiryId();

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        Course course = courseRepository.findById(inquiry.courseId().value())
                .orElseThrow(() -> new CourseNotFound(inquiry.courseId().value()));

        List<Comment> comments = commentRepository.findAllByInquiryId(inquiryId);

        Comment comment = Comment.of(inquiry, comments, commentRequestDto, account, course);

        Comment saved = commentRepository.save(comment);

        return saved.toCommentDto(new AccountId(account.id()));
    }

    public CommentDeleteDto delete(Long commentId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));

        comment.removeAllProperties(new AccountId(account.id()));

        return comment.toCommentDeleteDto();
    }

    public CommentDto update(Long commentId, String content, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));

        comment.updateContent(content, new AccountId(account.id()));

        return comment.toCommentDto();
    }
}
