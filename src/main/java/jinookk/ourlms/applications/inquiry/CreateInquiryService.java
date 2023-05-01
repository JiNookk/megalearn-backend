package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateInquiryService {
    private final InquiryRepository inquiryRepository;
    private final AccountRepository accountRepository;

    public CreateInquiryService(InquiryRepository inquiryRepository,
                                AccountRepository accountRepository) {
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
    }

    public InquiryDto create(InquiryRequestDto inquiryRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Inquiry inquiry = Inquiry.of(inquiryRequestDto, accountId, account.name());

        Inquiry saved = inquiryRepository.save(inquiry);

        return saved.toInquiryDto();
    }
}
