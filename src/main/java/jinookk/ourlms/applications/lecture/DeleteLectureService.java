package jinookk.ourlms.applications.lecture;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.exceptions.LectureNotFound;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.repositories.LectureRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteLectureService {
    private final LectureRepository lectureRepository;

    public DeleteLectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @CacheEvict(cacheNames = "lectureCache", key = "#lectureId")
    public LectureDto delete(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFound(lectureId));

        lecture.delete();

        return lecture.toLectureDto();
    }
}
