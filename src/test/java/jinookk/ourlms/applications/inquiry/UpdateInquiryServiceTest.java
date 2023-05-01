package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateInquiryServiceTest {
    UpdateInquiryService updateInquiryService;
    InquiryRepository inquiryRepository;

    @BeforeEach
    void setup() {
        inquiryRepository = mock(InquiryRepository.class);
        updateInquiryService = new UpdateInquiryService(inquiryRepository);

        Inquiry inquiry1 = Inquiry.fake("inquiry1");
        Inquiry inquiry2 = Inquiry.fake("inquiry2");

        given(inquiryRepository.save(any())).willReturn(inquiry1);

        given(inquiryRepository.findAllByLectureId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry1));

        given(inquiryRepository.findAllByAccountId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(inquiry1, inquiry2));
    }

    @Test
    void update() {
        String title = "updated";
        List<String> hashTags = List.of("태그 1", "태그 2", "태그 3");
        String content = "updated";

        InquiryUpdateDto inquiryUpdateDto = new InquiryUpdateDto(title,hashTags,content);

        InquiryDto inquiryDto = updateInquiryService.update(new InquiryId(1L), inquiryUpdateDto);

        assertThat(inquiryDto.getId()).isEqualTo(1L);
    }

    @Test
    void toggleSolved() {
        InquiryDto inquiryDto = updateInquiryService.toggleSolved(new InquiryId(1L));

        assertThat(inquiryDto.getStatus().getSolved()).isEqualTo("completed");
    }

    @Test
    void increaseHits() {
        InquiryDto inquiryDto = updateInquiryService.increaseHits(new InquiryId(1L));

        assertThat(inquiryDto.getHits()).isEqualTo(1);
    }
}
