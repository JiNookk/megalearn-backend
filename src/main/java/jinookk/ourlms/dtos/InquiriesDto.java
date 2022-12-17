package jinookk.ourlms.dtos;

import java.util.List;

public class InquiriesDto {
    private List<InquiryDto> inquiries;

    public InquiriesDto() {
    }

    public InquiriesDto(List<InquiryDto> inquiries) {
        this.inquiries = inquiries;
    }

    public List<InquiryDto> getInquiries() {
        return inquiries;
    }
}
