package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.exceptions.LectureNotFound;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateLectureService {
    private final LectureRepository lectureRepository;

    public UpdateLectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDto update(Long lectureId, LectureUpdateRequestDto lectureUpdateRequestDto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        lecture.update(lectureUpdateRequestDto);

        return lecture.toLectureDto();
    }
}
