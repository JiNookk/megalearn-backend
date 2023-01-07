package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.ids.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByAccountId(AccountId accountId);
}
