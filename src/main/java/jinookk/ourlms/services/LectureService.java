package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.exceptions.LectureNotFound;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;

    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
    }

    public LectureDto detail(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        return lecture.toLectureDto();
    }

    public LecturesDto list(CourseId courseId) {
        List<Lecture> lectures = lectureRepository.findAllByCourseId(courseId);

        List<LectureDto> lectureDtos = lectures.stream()
                .map(Lecture::toLectureDto)
                .toList();

        return new LecturesDto(lectureDtos);
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
