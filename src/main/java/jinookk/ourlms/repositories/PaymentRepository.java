package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByAccountId(AccountId accountId);

    List<Payment> findAllByCourseId(CourseId courseId);
}
