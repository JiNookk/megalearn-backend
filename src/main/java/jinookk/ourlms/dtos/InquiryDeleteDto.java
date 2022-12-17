package jinookk.ourlms.dtos;

public class InquiryDeleteDto {
    private Long inquiryId;

    public InquiryDeleteDto() {
    }

    public InquiryDeleteDto(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }
}
