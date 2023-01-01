package jinookk.ourlms.dtos;

import java.util.List;

public class InstructorInquiriesDto {
    List<InstructorInquiryDto> inquiries;

    public InstructorInquiriesDto() {
    }

    public InstructorInquiriesDto(List<InstructorInquiryDto> inquiries) {
        this.inquiries = inquiries;
    }

    public List<InstructorInquiryDto> getInquiries() {
        return inquiries;
    }
}
