package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateInquiryService {
    private final InquiryRepository inquiryRepository;

    public UpdateInquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public InquiryDto update(InquiryId inquiryId, InquiryUpdateDto inquiryUpdateDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        inquiry.update(inquiryUpdateDto);

        return inquiry.toInquiryDto();
    }

    public InquiryDto toggleSolved(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        Inquiry toggled = inquiry.toggleSolved();

        return toggled.toInquiryDto();
    }

    public InquiryDto increaseHits(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        Inquiry increased = inquiry.increaseHits();

        return increased.toInquiryDto();
    }
}
