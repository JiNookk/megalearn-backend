package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteInquiryService {
    private final InquiryRepository inquiryRepository;

    public DeleteInquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public InquiryDeleteDto delete(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        inquiry.delete();

        return inquiry.toInquiryDeleteDto();
    }
}
