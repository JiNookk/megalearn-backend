package jinookk.ourlms.services;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.AccountId;
import jinookk.ourlms.models.vos.InquiryId;
import jinookk.ourlms.models.vos.LectureId;
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

class InquiryServiceTest {
    InquiryService inquiryService;
    InquiryRepository inquiryRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        inquiryService = new InquiryService(inquiryRepository, accountRepository);

        Inquiry inquiry = Inquiry.fake("test");
        given(inquiryRepository.save(any())).willReturn(inquiry);

        given(inquiryRepository.findAllByLectureId(new LectureId(1L))).willReturn(List.of(inquiry));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry));

        Account account = Account.fake("tester");
        given(accountRepository.findById(1L)).willReturn(Optional.of(account));
    }

    @Test
    void detail() {
        InquiryDto inquiryDto = inquiryService.detail(new InquiryId(1L));

        assertThat(inquiryDto).isNotNull();
    }

    @Test
    void list() {
        InquiriesDto inquiriesDto = inquiryService.list(new LectureId(1L), null, null);

        assertThat(inquiriesDto.getInquiries()).hasSize(1);
    }

    @Test
    void create() {
        InquiryRequestDto inquiryRequestDto =
                new InquiryRequestDto(new LectureId(1L), List.of("JPA"), "test", "tester", true, 1L, 24L);

        AccountId userId = new AccountId(1L);

        InquiryDto inquiryDto = inquiryService.create(inquiryRequestDto, userId);

        assertThat(inquiryDto.getContent()).isEqualTo("test");
    }

    @Test
    void update() {
        String title = "updated";
        List<String> hashTags = List.of("태그 1", "태그 2", "태그 3");
        String content = "updated";

        InquiryUpdateDto inquiryUpdateDto = new InquiryUpdateDto(title,hashTags,content);

        InquiryDto inquiryDto = inquiryService.update(new InquiryId(1L), inquiryUpdateDto);

        assertThat(inquiryDto.getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        InquiryDeleteDto inquiryDeleteDto = inquiryService.delete(new InquiryId(1L));

        assertThat(inquiryDeleteDto.getInquiryId()).isEqualTo(1L);
    }
}
