package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.SectionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findAllByAccountIdAndSectionId(AccountId accountId, SectionId sectionId);

    Optional<Progress> findByLectureId(LectureId lectureId);

    List<Progress> findAllByCourseId(CourseId courseId);

    List<Progress> findAllByAccountId(AccountId accountId);
}
