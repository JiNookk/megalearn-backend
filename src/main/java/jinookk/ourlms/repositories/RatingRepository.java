package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByCourseId(CourseId accountId);

    List<Rating> findAllByAccountId(AccountId accountId);
}
