package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.models.vos.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.InquiryId;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Status;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private static Comment fake(Content content) {
        return new Comment(11L, new InquiryId(1L), new AccountId(1L), new Status(Status.CREATED),
                new Name("1st Tester"), content, LocalDateTime.now());
    }

    public static Comment fake(String content) {
        return fake(new Content(content));
    }

    public CommentDto toCommentDto() {
        return new CommentDto(id, author, content, publishTime);
    }

    public CommentDto toCommentDto(AccountId accountId) {
        Boolean isMyComment = isMyComment(accountId);
        return new CommentDto(id, author, content, publishTime, isMyComment);
    }

    public void removeAllProperties() {
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

    public void updateContent(String value) {
        this.content.update(value);
    }

    public Boolean isMyComment(AccountId accountId) {
        return Objects.equals(accountId, this.accountId);
    }
}
