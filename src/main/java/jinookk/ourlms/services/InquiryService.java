package jinookk.ourlms.services;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.AccountId;
import jinookk.ourlms.models.vos.InquiryId;
import jinookk.ourlms.models.vos.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final AccountRepository accountRepository;

    public InquiryService(InquiryRepository inquiryRepository, AccountRepository accountRepository) {
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
    }

    public InquiryDto create(InquiryRequestDto inquiryRequestDto, AccountId accountId) {
        Account account = accountRepository.findById(accountId.value())
                .orElseThrow(() -> new AccountNotFound(accountId));

        Inquiry inquiry = Inquiry.of(inquiryRequestDto, accountId, account.name());

        Inquiry saved = inquiryRepository.save(inquiry);

        return saved.toInquiryDto();
    }

    public InquiriesDto list(LectureId lectureId, Long lectureTime, String content) {
        List<Inquiry> inquiries = inquiryRepository.findAllByLectureId(lectureId);

        if (lectureTime != null && content == null) {
            inquiries = inquiryRepository.findAllByLectureTime_MinuteAndLectureId(lectureTime, lectureId);
        }

        if (lectureTime == null && content != null) {
            inquiries = inquiryRepository
                    .findAllByTitle_ValueContainsOrContent_ValueContainsOrPublisher_ValueContainsAndLectureId(
                            content, content, content, lectureId);
        }

        if (lectureTime != null && content != null) {
            inquiries = inquiryRepository
                    .findAllByTitle_ValueContainsOrContent_ValueContainsOrPublisher_ValueContainsAndLectureTime_MinuteAndLectureId(
                            content, content, content, lectureTime, lectureId);
        }

        List<InquiryDto> inquiryDtos = inquiries.stream()
                .map(Inquiry::toInquiryDto)
                .toList();

        return new InquiriesDto(inquiryDtos);
    }

    public InquiryDto detail(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        return inquiry.toInquiryDto();
    }

    public InquiryDeleteDto delete(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        inquiry.delete();

        return inquiry.toInquiryDeleteDto();
    }

    public InquiryDto update(InquiryId inquiryId, InquiryUpdateDto inquiryUpdateDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        inquiry.update(inquiryUpdateDto);

        return inquiry.toInquiryDto();
    }
}
