package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateLectureService {
    private final LectureRepository lectureRepository;

    public CreateLectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDto create(LectureRequestDto lectureRequestDto) {
        Lecture lecture = Lecture.of(lectureRequestDto);

        Lecture saved = lectureRepository.save(lecture);

        return saved.toLectureDto();
    }
}
