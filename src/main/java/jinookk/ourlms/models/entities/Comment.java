package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.status.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "inquiry_id"))
    private InquiryId inquiryId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private Status status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "author"))
    private Name author;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content"))
    private Content content;

    @CreationTimestamp
    private LocalDateTime publishTime;

    public Comment() {
    }

    public Comment(Long id, InquiryId inquiryId, AccountId accountId, Status status, Name author,
                   Content content, LocalDateTime publishTime) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.accountId = accountId;
        this.status = status;
        this.author = author;
        this.content = content;
        this.publishTime = publishTime;
    }

    public static Comment of(Inquiry inquiry, List<Comment> comments, CommentRequestDto commentRequestDto,
                             Account account, Course course) {
        if (course.isInstructor(new AccountId(account.id()))) {
            inquiry.reply();
        }

        if (inquiry.isPublisherId(new AccountId(account.id()))) {
            return Comment.of(commentRequestDto, inquiry.publisher(), inquiry.accountId());
        }

        Optional<Comment> previousComment = inquiry.previousComment(comments, new AccountId(account.id()));

        if (previousComment.isPresent()) {
            Name author = previousComment.get().author();
            return Comment.of(commentRequestDto, author, new AccountId(account.id()));
        }

        Name author = new Name(account.name().value(), inquiry.anonymous());

        return Comment.of(commentRequestDto, author, new AccountId(account.id()));
    }

    public Long id() {
        return id;
    }

    public InquiryId inquiryId() {
        return inquiryId;
    }

    public Status status() {
        return status;
    }

    public Name author() {
        return author;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Content content() {
        return content;
    }

    public LocalDateTime publishTime() {
        return publishTime;
    }

    public static Comment of(CommentRequestDto commentRequestDto, Name publisher, AccountId accountId) {
        Long id = null;
        InquiryId inquiryId = commentRequestDto.getInquiryId();
        Status status = new Status(Status.CREATED);
        Content content = new Content(commentRequestDto.getContent());
        LocalDateTime publishTime = LocalDateTime.now();

        return new Comment(id, inquiryId, accountId, status, publisher, content, publishTime);
    }

    public CommentDto toCommentDto() {
        return new CommentDto(id, author, content, publishTime);
    }

    public CommentDto toCommentDto(AccountId accountId) {
        Boolean isMyComment = isMyComment(accountId);
        return new CommentDto(id, author, content, publishTime, isMyComment);
    }

    public void removeAllProperties(AccountId accountId) {
        if (!isMyComment(accountId)) {
            throw new AccessDeniedException("accountId: " + accountId + " has no authority for delete comment!");
        }

        inquiryId = null;
        accountId = null;
        status.delete();
        author = null;
        content = null;
        publishTime = null;
    }

    public CommentDeleteDto toCommentDeleteDto() {
        return new CommentDeleteDto(id);
    }

    public void updateContent(String value, AccountId accountId) {
        if (!isMyComment(accountId)) {
            throw new AccessDeniedException("accountId: " + accountId + " has no authority for update comment!");
        }

        this.content = new Content(value);
    }

    public Boolean isMyComment(AccountId accountId) {
        return Objects.equals(accountId, this.accountId);
    }
}
