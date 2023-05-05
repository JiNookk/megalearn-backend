package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.fixtures.Fixture;
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

class DeleteInquiryServiceTest {
    DeleteInquiryService deleteInquiryService;
    InquiryRepository inquiryRepository;

    @BeforeEach
    void setup() {
        inquiryRepository = mock(InquiryRepository.class);
        deleteInquiryService = new DeleteInquiryService(inquiryRepository);

        Inquiry inquiry1 = Fixture.inquiry("inquiry1");
        Inquiry inquiry2 = Fixture.inquiry("inquiry2");

        given(inquiryRepository.save(any())).willReturn(inquiry1);

        given(inquiryRepository.findAllByLectureId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry1));

        given(inquiryRepository.findAllByAccountId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(inquiry1, inquiry2));
    }

    @Test
    void delete() {
        InquiryDeleteDto inquiryDeleteDto = deleteInquiryService.delete(new InquiryId(1L));

        assertThat(inquiryDeleteDto.getInquiryId()).isEqualTo(1L);
    }
}
