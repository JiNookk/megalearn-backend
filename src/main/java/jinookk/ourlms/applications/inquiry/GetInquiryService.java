package jinookk.ourlms.applications.inquiry;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.InquiryRepository;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GetInquiryService {
    private final InquiryRepository inquiryRepository;
    private final AccountRepository accountRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    public GetInquiryService(InquiryRepository inquiryRepository, AccountRepository accountRepository,
                             CourseRepository courseRepository, LectureRepository lectureRepository) {
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
    }

    public InquiriesDto list(LectureId lectureId, Long lectureTime, String content) {
        List<Inquiry> inquiries = inquiryRepository.findAllByLectureId(lectureId).stream()
                .filter(inquiry -> !inquiry.status().value().equals("deleted"))
                .toList();

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

        return new InquiriesDto(inquiries.stream()
                .map(Inquiry::toInquiryDto)
                .toList());
    }

    public InquiriesDto list(UserName userName, InquiryFilterDto inquiryFilterDto) {
        if (userName == null) {
            return new InquiriesDto(inquiryRepository.findAll().stream()
                    .filter(inquiry -> !inquiry.status().value().equals("deleted"))
                    .map(Inquiry::toInquiryDto).toList());
        }

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = courses(accountId, new CourseId(inquiryFilterDto.getCourseId()));

        List<Inquiry> inquiries = inquiries(courses);

        List<Inquiry> filtered = filtered(inquiries, inquiryFilterDto);

        List<InquiryDto> inquiryDtos = getInquiryDtos(filtered);

        return new InquiriesDto(inquiryDtos);
    }

    public InquiriesDto listByCourseId(CourseId courseId) {
        List<Inquiry> inquiries = inquiryRepository.findAllByCourseId(courseId).stream()
                .filter(inquiry -> !inquiry.status().value().equals("deleted"))
                .toList();

        List<InquiryDto> inquiryDtos = inquiries.stream().map(Inquiry::toInquiryDto).toList();

        return new InquiriesDto(inquiryDtos);
    }

    public InquiryDto detail(InquiryId inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId.value())
                .orElseThrow(() -> new InquiryNotFound(inquiryId));

        return inquiry.toInquiryDto();
    }

    public InquiriesDto myInquiries(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Inquiry> inquiries = inquiryRepository.findAllByAccountId(accountId);

        List<InquiryDto> inquiryDtos = inquiries.stream()
                .map(Inquiry::toInquiryDto)
                .toList();

        return new InquiriesDto(inquiryDtos);
    }

    private List<Course> courses(AccountId accountId, CourseId courseId) {
        if (accountId.value() == null) {
            return courseRepository.findAll();
        }

        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        // 검색이 되려면 어떤식으로 진행해야 할까?
        return courseId.value() == null
                ? courses
                : courses.stream()
                .filter(course -> Objects.equals(course.id(), courseId.value()))
                .toList();
    }

    private List<Inquiry> inquiries(List<Course> courses) {
        return courses.stream()
                .map(course -> lectureRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(lecture -> inquiryRepository.findAllByLectureId(new LectureId(lecture.id())))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Inquiry> filtered(List<Inquiry> inquiries, InquiryFilterDto inquiryFilterDto) {
        Comparator<Inquiry> comparator = comparator(inquiryFilterDto.getOrder());

        return inquiries.stream()
                .filter(inquiry -> inquiry.filter(inquiryFilterDto.getType()))
                .sorted(comparator)
                .toList();
    }

    private Comparator<Inquiry> comparator(String order) {
        Comparator<Inquiry> comparator = Comparator.comparing(Inquiry::publishTime);

        if (order != null && order.equals("reply")) {
            comparator = Comparator.comparing(Inquiry::repliedAt);
        }

        return comparator;
    }

    private List<InquiryDto> getInquiryDtos(List<Inquiry> inquiries) {
        return inquiries.stream()
                .map(Inquiry::toInquiryDto)
                .toList();
    }
}
