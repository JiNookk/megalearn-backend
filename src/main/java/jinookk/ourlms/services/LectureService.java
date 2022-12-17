package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.exceptions.LectureNotFound;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDto detail(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        return lecture.toLectureDto();
    }

    public LecturesDto list(Long courseId) {
        List<Lecture> lectures = lectureRepository.findAllByCourseId(courseId);

        List<LectureDto> lectureDtos = lectures.stream()
                .map(Lecture::toLectureDto)
                .toList();

        return new LecturesDto(lectureDtos);
    }
}
