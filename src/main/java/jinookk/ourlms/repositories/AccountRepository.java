package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
