package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.exceptions.LectureNotFound;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LectureRepository;
import jinookk.ourlms.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;

    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository,
                          PaymentRepository paymentRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
        this.paymentRepository = paymentRepository;
    }

    public LectureDto detail(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        return lecture.toLectureDto();
    }

    public LecturesDto list() {
        List<Lecture> lectures = lectureRepository.findAll();

        List<LectureDto> lectureDtos = lectures.stream()
                .map(Lecture::toLectureDto)
                .toList();

        return new LecturesDto(lectureDtos);
    }

    public LecturesDto listByCourseId(CourseId courseId) {
        List<Lecture> lectures = lectureRepository.findAllByCourseId(courseId);

        List<LectureDto> lectureDtos = lectures.stream()
                .map(Lecture::toLectureDto)
                .toList();

        return new LecturesDto(lectureDtos);
    }

    public LecturesDto myLectures(AccountId accountId) {
        List<LectureDto> lectures = paymentRepository.findAllByAccountId(accountId).stream()
                        .map(Payment::courseId)
                        .map(lectureRepository::findAllByCourseId)
                        .flatMap(Collection::stream)
                        .map(Lecture::toLectureDto)
                        .toList();

        return new LecturesDto(lectures);
    }

    public LecturesDto listByInstructorId(AccountId accountId) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<LectureDto> lectureDtos = courses.stream()
                .map(course -> lectureRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Lecture::toLectureDto)
                .toList();

        return new LecturesDto(lectureDtos);
    }

    public LectureDto create(LectureRequestDto lectureRequestDto) {
        Lecture lecture = Lecture.of(lectureRequestDto);

        Lecture saved = lectureRepository.save(lecture);

        return saved.toLectureDto();
    }

    public LectureDto update(Long lectureId, LectureUpdateRequestDto lectureUpdateRequestDto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        lecture.update(lectureUpdateRequestDto);

        return lecture.toLectureDto();
    }

    public LectureDto delete(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        lecture.delete();

        return lecture.toLectureDto();
    }
}
