package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenValue(String expiredToken);
}
