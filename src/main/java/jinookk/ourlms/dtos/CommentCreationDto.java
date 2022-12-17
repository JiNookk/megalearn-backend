package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.InquiryId;
import jinookk.ourlms.models.vos.Name;

public class CommentCreationDto {
    private Long accountId;
    private InquiryId inquiryId;
    private Name publisher;
    private String content;

    public CommentCreationDto() {
    }

    public CommentCreationDto(Long accountId, InquiryId inquiryId, Name publisher, String content) {
        this.accountId = accountId;
        this.inquiryId = inquiryId;
        this.publisher = publisher;
        this.content = content;
    }

    public CommentCreationDto(Long accountId, InquiryId inquiryId, Name name, String content, Boolean anonymous) {
        this.accountId = accountId;
        this.inquiryId = inquiryId;
        this.publisher = new Name(name.value(), anonymous);
        this.content = content;
    }

    public static CommentCreationDto of(CommentRequestDto commentRequestDto, Long accountId, Name publisher) {
        return new CommentCreationDto(
                accountId, commentRequestDto.getInquiryId(), publisher, commentRequestDto.getContent());
    }

    public Long getAccountId() {
        return accountId;
    }

    public InquiryId getInquiryId() {
        return inquiryId;
    }

    public Name getPublisher() {
        return publisher;
    }

    public String getContent() {
        return content;
    }

    public static CommentCreationDto of(CommentRequestDto commentRequestDto,
                                        Long accountId, Name name, Boolean anonymous) {
        return new CommentCreationDto(
                accountId,commentRequestDto.getInquiryId(), name, commentRequestDto.getContent(), anonymous);
    }
}
