package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateInquiryServiceTest {
    CreateInquiryService createInquiryService;
    InquiryRepository inquiryRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        createInquiryService = new CreateInquiryService(inquiryRepository, accountRepository);

        Inquiry inquiry1 = Inquiry.fake("inquiry1");
        Inquiry inquiry2 = Inquiry.fake("inquiry2");

        given(inquiryRepository.save(any())).willReturn(inquiry1);

        given(inquiryRepository.findAllByLectureId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry1));

        given(inquiryRepository.findAllByAccountId(any())).willReturn(List.of(inquiry1, inquiry2));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));

        given(inquiryRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(inquiry1, inquiry2));
    }

    @Test
    void create() {
        InquiryRequestDto inquiryRequestDto =
                new InquiryRequestDto(new LectureId(1L), List.of("JPA"), "test", "tester", true, 1, 24, 1L);

        UserName userName = new UserName("userName@email.com");

        InquiryDto inquiryDto = createInquiryService.create(inquiryRequestDto, userName);

        assertThat(inquiryDto.getContent()).isEqualTo("inquiry1");
    }
}
