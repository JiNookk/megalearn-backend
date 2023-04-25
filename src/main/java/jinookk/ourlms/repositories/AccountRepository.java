package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName(UserName userName);

    Boolean existsByUserName(UserName userName);
}
