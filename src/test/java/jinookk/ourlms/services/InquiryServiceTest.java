package jinookk.ourlms.services;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import jinookk.ourlms.repositories.LectureRepository;
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
    CourseRepository courseRepository;
    LectureRepository lectureRepository;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        lectureRepository = mock(LectureRepository.class);
        accountRepository = mock(AccountRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        inquiryService = new InquiryService(inquiryRepository, accountRepository, courseRepository, lectureRepository);

        Inquiry inquiry1 = Inquiry.fake("inquiry1");
        Inquiry inquiry2 = Inquiry.fake("inquiry2");

        given(inquiryRepository.save(any())).willReturn(inquiry1);

        given(inquiryRepository.findAllByLectureId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry1));

        Account account = Account.fake("user");
        given(accountRepository.findById(1L)).willReturn(Optional.of(account));

        Course course = Course.fake("course");
        given(courseRepository.findAllByAccountId(new AccountId(1L))).willReturn(List.of(course));

        Lecture lecture1 = Lecture.fake("lecture1");
        Lecture lecture2 = Lecture.fake("lecture2");
        given(lectureRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(lecture1, lecture2));
    }

    @Test
    void detail() {
        InquiryDto inquiryDto = inquiryService.detail(new InquiryId(1L));

        assertThat(inquiryDto).isNotNull();
    }

    @Test
    void list() {
        InquiriesDto inquiriesDto = inquiryService.list(new LectureId(1L), null, null);

        assertThat(inquiriesDto.getInquiries()).hasSize(2);
    }

    @Test
    void create() {
        InquiryRequestDto inquiryRequestDto =
                new InquiryRequestDto(new LectureId(1L), List.of("JPA"), "test", "tester", true, 1L, 24L, 1L);

        AccountId userId = new AccountId(1L);

        InquiryDto inquiryDto = inquiryService.create(inquiryRequestDto, userId);

        assertThat(inquiryDto.getContent()).isEqualTo("inquiry1");
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

    @Test
    void listWithInstructorInquiries() {
        InquiriesDto inquiriesDto = inquiryService.list(new AccountId(1L), new InquiryFilterDto(null, null, null));

        assertThat(inquiriesDto.getInquiries()).hasSize(4);
    }
}
