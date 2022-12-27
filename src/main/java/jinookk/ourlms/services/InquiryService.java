package jinookk.ourlms.services;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
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
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final AccountRepository accountRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    public InquiryService(InquiryRepository inquiryRepository, AccountRepository accountRepository,
                          CourseRepository courseRepository, LectureRepository lectureRepository) {
        this.inquiryRepository = inquiryRepository;
        this.accountRepository = accountRepository;
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
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

        return new InquiriesDto(inquiries.stream()
                .map(Inquiry::toInquiryDto)
                .toList());
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

    public InquiriesDto list(AccountId accountId, InquiryFilterDto inquiryFilterDto) {
        List<Course> courses = courses(accountId, new CourseId(inquiryFilterDto.getCourseId()));

        List<Inquiry> inquiries = inquiries(courses);

        List<Inquiry> filtered = filtered(inquiries, inquiryFilterDto);

        List<InquiryDto> inquiryDtos = getInquiryDtos(filtered);

        return new InquiriesDto(inquiryDtos);
    }

    private List<Course> courses(AccountId accountId, CourseId courseId) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        // 검색이 되려면 어떤식으로 진행해야 할까? 일단 제쳐두고 노아님에게 물어보는게 나을까?
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

        if (order != null && order.equals("like")) {
            comparator = (p1, p2) -> Integer.compare(p2.countOfLikes(), p1.countOfLikes());
        }

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
