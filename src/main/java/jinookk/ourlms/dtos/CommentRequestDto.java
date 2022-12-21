package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.ids.InquiryId;

import javax.validation.constraints.NotBlank;

public class CommentRequestDto {
    private InquiryId inquiryId;

    @NotBlank
    private String content;

    public CommentRequestDto() {
    }

    public CommentRequestDto(InquiryId inquiryId, String content) {
        this.inquiryId = inquiryId;
        this.content = content;
    }

    public InquiryId getInquiryId() {
        return inquiryId;
    }

    public String getContent() {
        return content;
    }
}
