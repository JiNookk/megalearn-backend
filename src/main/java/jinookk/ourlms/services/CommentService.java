package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CommentNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CommentRepository;
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

    public CommentService(CommentRepository commentRepository, InquiryRepository inquiryRepository,
                          AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
    }

    public CommentsDto list(InquiryId inquiryId, AccountId accountId) {
        List<Comment> comments = commentRepository.findAllByInquiryId(inquiryId);


        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> comment.toCommentDto(accountId))
                .toList();

        return new CommentsDto(commentDtos);
    }

    // TODO : 댓글을 POST하는 메서드 -> 익명 댓글 기능 -> 이전에 달았던 댓글이 있으면 작성자명 그대로 가져오기
    // TODO : 댓글을 만드는 역할 -> 질문이 가장 적합하다!
    // TODO : 테스트 필요 -> 도메인 모델에서 비즈니스 로직테스트 하고 나머지 부분 테스트하기
    // TODO : 지금 가지고 있는 객체들 사이에서 어떤게 가장 적합할지 고민해보기
    // TODO : 행동을 정하고 적합한 객체를 양쪽에 배치하자.
    public CommentDto create(CommentRequestDto commentRequestDto, AccountId accountId) {
        InquiryId inquiryId = commentRequestDto.getInquiryId();

        Account account = accountRepository.findById(accountId.value())
                .orElseThrow(() -> new AccountNotFound(accountId));

        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        List<Comment> comments = commentRepository.findAllByInquiryId(inquiryId);

        Comment comment = inquiry.createComment(comments, commentRequestDto, account);

        // 댓글이 만드는 방식으로 리팩토링하기
//        Comment comment = Comment.of()

        Comment saved = commentRepository.save(comment);

        return saved.toCommentDto(accountId);
    }

    public CommentDeleteDto delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));

        comment.removeAllProperties();

        return comment.toCommentDeleteDto();
    }

    public CommentDto update(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));

        comment.updateContent(content);

        return comment.toCommentDto();
    }
}
