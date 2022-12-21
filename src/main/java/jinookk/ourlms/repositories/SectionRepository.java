package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByCourseId(CourseId courseId);
}
