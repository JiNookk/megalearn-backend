package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByCourseId(Long courseId);
}
