package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.vos.UserName;
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

class GetInquiryServiceTest {
    GetInquiryService getInquiryService;
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
        getInquiryService = new GetInquiryService(inquiryRepository, accountRepository, courseRepository, lectureRepository);

        Inquiry inquiry1 = Inquiry.fake("inquiry1");
        Inquiry inquiry2 = Inquiry.fake("inquiry2");

        given(inquiryRepository.save(any())).willReturn(inquiry1);

        given(inquiryRepository.findAllByLectureId(any())).willReturn(List.of(inquiry1, inquiry2));

        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry1));

        given(inquiryRepository.findAllByAccountId(any())).willReturn(List.of(inquiry1, inquiry2));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));

        Course course = Course.fake("course");
        given(courseRepository.findAllByAccountId(new AccountId(1L))).willReturn(List.of(course));

        Lecture lecture1 = Lecture.fake("lecture1");
        Lecture lecture2 = Lecture.fake("lecture2");
        given(lectureRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(lecture1, lecture2));

        given(inquiryRepository.findAllByCourseId(new CourseId(1L))).willReturn(List.of(inquiry1, inquiry2));
    }

    @Test
    void detail() {
        InquiryDto inquiryDto = getInquiryService.detail(new InquiryId(1L));

        assertThat(inquiryDto).isNotNull();
    }

    @Test
    void list() {
        InquiriesDto inquiriesDto = getInquiryService.list(new LectureId(1L), null, null);

        assertThat(inquiriesDto.getInquiries()).hasSize(2);
    }

    @Test
    void myInquiries() {
        InquiriesDto inquiriesDto = getInquiryService.myInquiries(new UserName("userName@email.com"));

        assertThat(inquiriesDto.getInquiries()).hasSize(2);
    }

    @Test
    void listByCourseId() {
        InquiriesDto inquiriesDto = getInquiryService.listByCourseId(new CourseId(1L));

        assertThat(inquiriesDto.getInquiries()).hasSize(2);
    }

    @Test
    void listWithInstructorInquiries() {
        InquiriesDto inquiriesDto = getInquiryService.list(new UserName("userName@email.com"), new InquiryFilterDto(null, null, null));

        assertThat(inquiriesDto.getInquiries()).hasSize(4);
    }
}
