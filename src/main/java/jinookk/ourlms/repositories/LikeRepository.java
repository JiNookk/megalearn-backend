package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByAccountIdAndCourseId(AccountId accountId, CourseId courseId);

    List<Like> findAllByAccountId(AccountId accountId);
}
